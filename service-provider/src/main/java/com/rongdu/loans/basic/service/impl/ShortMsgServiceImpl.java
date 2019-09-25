package com.rongdu.loans.basic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.security.Digests;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.basic.entity.BasicBlacklist;
import com.rongdu.loans.basic.entity.SmsLog;
import com.rongdu.loans.basic.manager.BasicBlacklistManager;
import com.rongdu.loans.basic.manager.SmsLogManager;
import com.rongdu.loans.basic.option.BasicBlacklistOP;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.MsgTempleteEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.RepayPlanItemDetail;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service("shortMsgService")
public class ShortMsgServiceImpl extends BaseService implements ShortMsgService {

	@Autowired
	private SmsLogManager smsLogManager;

	@Autowired
	private BasicBlacklistManager basicBlacklistManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
    private RepayPlanItemManager repayPlanItemManager;

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Override
	public void sendMsg(SendShortMsgOP sendShortMsgOP) {
		if (null == sendShortMsgOP) {
			logger.error("the param sendShortMsgOP is null");
			return;
		}
		//send(sendShortMsgOP.getMobile(), sendShortMsgOP.getMessage(), sendShortMsgOP.getChannelId());
		this.sendMsgJuHe(sendShortMsgOP.getMobile(), sendShortMsgOP.getTplId(), sendShortMsgOP.getMessage());
		// 保存短信记录
		saveSmsLog(sendShortMsgOP, null);
	}

	@Override
	public String sendMsgCode(SendShortMsgOP sendShortMsgOP) {
		if (null == sendShortMsgOP) {
			logger.error("the param sendShortMsgOP is null");
			return null;
		}
		String msgVerCode = IdGen.genMsgCodeSix();
		String message = null;
		String mobile = sendShortMsgOP.getMobile();
		if (null != sendShortMsgOP.getMsgType()) {
			message = getMessageFromType(sendShortMsgOP.getMsgType(), msgVerCode);
		}
		if (null == message) {
			message = sendShortMsgOP.getMessage();
		} else {
			sendShortMsgOP.setMessage(message);
		}
		try {
			/** 发短信 */
			send(mobile, message, sendShortMsgOP.getChannelId());
			/** 保存短信记录 */
			saveSmsLog(sendShortMsgOP, msgVerCode);
			/** 当天发送条数统计缓存 以及 预警 */
			// statisticsAndWarn();

		} catch (Exception e) {
			logger.error("短信发送失败", e);
		}
		return msgVerCode;
	}

	/**
	 * 当天发送条数统计缓存 以及 预警
	 */
	private void statisticsAndWarn() {
		Integer count = (Integer) JedisUtils.getObject(Global.MW_SEND_DAY_COUNT);
		// 更新缓存中统计数
		if (null == count) {
			count = 1;
		} else {
			count++;
		}
		JedisUtils.setObject(Global.MW_SEND_DAY_COUNT, count, Global.ONE_DAY_CACHESECONDS);
		if (count % Global.MSG_DAY_WARN_RADIX == 0) {
			String mob = Global.getConfig("msg.warn.mobs");
			try {
				send(mob, ShortMsgTemplate.MSG_COUNT_WARN, null);
			} catch (Exception e) {
				logger.debug("短信发送量预警短信发送异常");
			}
		}
	}

	/**
	 * 根据信息类型获取短信信息
	 *
	 * @param msgType
	 * @param msgVerCode
	 * @return
	 */
	private String getMessageFromType(Integer msgType, String msgVerCode) {
		String rz = null;
		switch (msgType) {
		case 1: // 注册短信验证码
			rz = assembleMessage(ShortMsgTemplate.MSG_TEMP_REG, msgVerCode);
			break;
		case 2: // 忘记密码短信验证码
			rz = assembleMessage(ShortMsgTemplate.MSG_TEMP_FIND_PWD, msgVerCode);
			break;
		case 3: // 开通恒丰银行存管账号
			rz = assembleMessage(ShortMsgTemplate.MSG_TEMP_JXBANK, msgVerCode);
			break;
		case 5: // 短信登录密码
			rz = assembleMessage(ShortMsgTemplate.MSG_TEMP_LOGIN, msgVerCode);
			break;
		default:
			logger.warn("请输入有效的短信验证类型");
			return null;
		}
		return rz;
	}

	/**
	 * 拼装短信信息
	 *
	 * @param msg
	 * @param msgVerCode
	 * @return
	 */
	private String assembleMessage(String msg, String msgVerCode) {
		if (StringUtils.isNotBlank(msg)) {
			msg = msg.replaceFirst(ShortMsgTemplate.REPLACE_CODE, msgVerCode);
		}
		return msg;
	}

	/**
	 * 保存短信发送日志
	 *
	 * @param sendShortMsgOP
	 * @return
	 */
	public int saveSmsLog(SendShortMsgOP sendShortMsgOP, String msgVerCode) {
		if (null != sendShortMsgOP) {
			SmsLog smsLog = new SmsLog();
			smsLog.setSmsCode(msgVerCode);
			smsLog.setIp(sendShortMsgOP.getIp());
			smsLog.setContent(sendShortMsgOP.getMessage());
			smsLog.setMobile(sendShortMsgOP.getMobile());
			if (sendShortMsgOP.getMsgType() != null) {
				smsLog.setType(String.valueOf(sendShortMsgOP.getMsgType()));
			}
			smsLog.setUserId(sendShortMsgOP.getUserId());
			smsLog.setSendTime(new Date());
			smsLog.setSource(sendShortMsgOP.getSource());
			smsLog.setStatus(1);
			smsLog.setChannelCode(sendShortMsgOP.getProductId());
			smsLog.setChannelName(sendShortMsgOP.getChannelName());
			smsLog.setRemark(sendShortMsgOP.getRemark());
			smsLog.preInsert();
			return smsLogManager.saveSmsLog(smsLog);
		}
		return 0;
	}

	/**
	 * 生成20位流水号
	 *
	 * @return 20位流水号，YYYYMMddHHmmss（14位）+随机数（6位）
	 */
	private String genSeqNo() {
		DateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		String rdmNo = org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(RandomUtils.nextInt(0, 1000000)), 6,
				'0');
		return dateFormat.format(new Date()) + rdmNo;
	}

	private String send(String phone, String message, String channelId) throws Exception {
		String result = "";
		/*
		 * if (StringUtils.equals(Global.PROFILE, "test")){ return result; }
		 */
		// 扩展子号 （不带请填星号*，长度不大于6位）;
		// String strSubPort = "*";
		// 用户自定义流水号，不带请输入0（流水号范围-（2^63）……2^63-1）
		// String strUserMsgId = seqNo;
		// 短信息发送接口（相同内容群发，可自定义流水号）POST请求。
		result = sendSms(phone, message, channelId);
		return result;
	}

	private String sendSms(String strMobiles, String strMessage, String channelId) throws Exception {
		String result = "";// 定义返回值变量
		String seed = DateUtils.getHHmmss();
		StringBuffer sb = new StringBuffer();
		sb.append("name=" + Global.MONGATE_USER_ID);
		sb.append("&seed=" + seed);
		sb.append("&key=" + Digests.md5(Digests.md5(Global.MONGATE_PWD).toLowerCase() + seed).toLowerCase());// //md5(
																												// md5(password)
																												// +
																												// seed)
																												// )
		sb.append("&dest=" + strMobiles);

		if (ChannelEnum.TOUFULI.getCode().equals(channelId)) {
			sb.append("&content=" + "【复利小贷】" + URLEncoder.encode(strMessage, "UTF-8") + "退订回T");// 注意编码，字段编码要和接口所用编码一致，有可能出现汉字之类的记得转换编码
		} else if (ChannelEnum.CHENGDAI.getCode().equals(channelId)) {
			sb.append("&content=" + "【诚诚普惠】" + URLEncoder.encode(strMessage, "UTF-8") + "退订回T");
		} else if (ChannelEnum.LYFQAPP.getCode().equals(channelId)) {
			sb.append("&content=" + "【开心游】" + URLEncoder.encode(strMessage, "UTF-8") + "退订回T");
		} else if ("RONGJHH".equals(channelId) || "SLLAPIJHH".equals(channelId)) {// 融360-
																					// 聚花花
			strMessage = strMessage.replace("聚宝钱包", "");
			strMessage = strMessage.replace("4001622772", "4001075006");
			sb.append("&content=" + "【聚花花】" + URLEncoder.encode(strMessage, "UTF-8") + "退订回T");
		} else {
			sb.append("&content=" + "【聚宝钱包】" + URLEncoder.encode(strMessage, "UTF-8") + "退订回T");
		}
		sb.append("&ext=" + "");
		sb.append("&reference=" + "");

		logger.info("请求报文 :" + sb.toString());

		HttpURLConnection connection = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			// 创建url对象
			URL url = new URL(Global.MONGATE_SEND_SUBMIT_URL);
			// 打开url连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置url请求方式 ‘GET’ 或者 ‘POST’
			connection.setRequestMethod("POST");
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			printWriter.write(sb.toString());// post的参数 xx=xx&yy=yy
			// flush输出流的缓冲
			printWriter.flush();
			// 开始获取数据
			bis = new BufferedInputStream(connection.getInputStream());
			bos = new ByteArrayOutputStream();
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1) {
				bos.write(arr, 0, len);
				bos.flush();
			}
			result = bos.toString();
			logger.info("请求应答:" + result);
			bos.close();
		} catch (Exception e) {
			logger.error("发送POST请求出现异常!", e);
			e.printStackTrace();
		} finally {
			try {
				try {
					if (bos != null) {
						bos.close();
					}
					if (connection != null) {
						connection.disconnect();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 使用post请求
	 *
	 * @param obj
	 *            请求参数对象
	 * @param httpUrl
	 *            请求URL地址
	 * @return 请求网关的返回值
	 * @throws Exception
	 */
	private String executePost(Object obj, String httpUrl) throws Exception {
		String result = "";
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		// 设置请求参数
		String fieldName = null;
		String fieldNameUpper = null;
		Method getMethod = null;
		String value = null;
		// 循环设置请求参数
		for (int i = 0; i < fields.length; i++) {
			fieldName = fields[i].getName();
			fieldNameUpper = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
			// 通过反射获取get方法
			getMethod = cls.getMethod("get" + fieldNameUpper);
			// 通过反射调用get方法
			value = (String) getMethod.invoke(obj);
			if (value != null) {
				// 请求参数值不为空，才设置
				params.add(new BasicNameValuePair(fieldName, value));
			}
		}
		// 设置HttpPost
		HttpPost httppost = new HttpPost(httpUrl);
		// 设置参数的编码UTF-8
		httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// 创建HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// Http请求网关
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		// 返回值不为空，且长度大于0
		if (entity != null && entity.getContentLength() > 0) {
			// 将返回值转换成字符串
			result = EntityUtils.toString(entity);
		}
		// 处理返回结果, 关闭连接
		httpclient.getConnectionManager().shutdown();
		System.out.println("梦网响应结果：" + result);
		return result;
	}

	@Override
	public boolean isRegBlackList(String ip) {
		if (StringUtils.isBlank(ip)) {
			logger.error("the param ip is null");
			return true;
		}
		return smsLogManager.isRegBlackList(ip);
	}

	@Override
	public boolean isInBlackListTab(String ip, String mob) {
		// 初始化返回值
		boolean rz = false;
		if (StringUtils.isNotBlank(mob)) {
			// 判断手机号是否在黑名单中
			rz = basicBlacklistManager.isInBlacklist(mob);
			// 手机号不在黑名单中，再判断ip是否在黑名单中
			if (!rz) {
				if (StringUtils.isNotBlank(ip)) {
					// 判断ip是否在黑名单中
					return basicBlacklistManager.isInBlacklist(ip);
				}
			}
		}
		return rz;
	}

	@Override
	public int saveToBlackListTab(BasicBlacklistOP op) {
		// 参数判断
		if (null == op) {
			logger.error("the param op is null");
			return 0;
		}
		// 构造接口参数对象
		BasicBlacklist entity = new BasicBlacklist();
		entity.setBlType(op.getBlType());
		entity.setBlValue(op.getBlValue());
		entity.setChannel(op.getChannel());
		entity.setBlDate(DateUtils.getDate());
		entity.preInsert();
		return basicBlacklistManager.insert(entity);
	}

	@Override
	public boolean sendMsgYiMei(String seqid, String phones, String msg) {
		boolean result = false;
		int error = -1;
		Map<String, String> params = Maps.newHashMap();
		params.put("cdkey", Global.YIMEI_USER_ID);
		params.put("password", Global.YIMEI_PWD);
		params.put("phone", phones);
		params.put("message", msg);
		params.put("seqid", seqid);
		String xmlResult = "";
		try {
			xmlResult = RestTemplateUtils.getInstance().postForJson(Global.YIMEI_URL, params);
			String jsonResult = XMLUtil.xmlToJson(xmlResult);
			JSONObject json = JSONObject.parseObject(jsonResult);
			JSONObject responseJson = JSONObject.parseObject(json.getString("response"));
			error = responseJson.getInteger("error");
			if (error == 0) {
				result = true;
			} else {
				logger.error("通过亿美发送短信错误：" + xmlResult);
			}
		} catch (Exception e) {
			logger.error("通过亿美发送短信出现异常：" + e.getMessage());
		}
		return result;
	}

	@Override
	public boolean sendMsgXuanWu(String phones, String msg) {
		boolean result = false;
		Map<String, String> params = Maps.newHashMap();
		params.put("userid", Global.XUANWU_USER_ID);
		params.put("account", Global.XUANWU_ACCOUNT);
		params.put("password", Global.XUANWU_PWD);
		params.put("mobile", phones);
		params.put("content", msg);
		params.put("action", "send");
		params.put("sendTime", null);
		params.put("extno", null);
		String xmlResult = "";
		try {
			xmlResult = RestTemplateUtils.getInstance().postForJson(Global.XUANWU_URL, params);
			String jsonResult = XMLUtil.xmlToJson(xmlResult);
			JSONObject json = JSONObject.parseObject(jsonResult);
			JSONObject responseJson = JSONObject.parseObject(json.getString("returnsms"));
			String returnData = responseJson.getString("returnstatus");
			if (returnData.equals("Success")) {
				result = true;
			} else {
				logger.error("玄武发送短信错误：" + xmlResult);
			}
		} catch (Exception e) {
			logger.error("玄武发送短信出现异常：" + e.getMessage());
		}
		return result;
	}

	@Override
	public void sendMsgComplainRecord(String mobile, String template) {
		try {
			sendMsgKjcx(mobile, template);
		} catch (Exception e) {
			logger.error("工单提醒短信发送异常：" + e.getMessage());
		}
	}

	@Override
    public void sendShortMsg(SendShortMsgOP sendShortMsgOP) {
        MsgTempleteEnum msgTempleteEnum = MsgTempleteEnum.get(sendShortMsgOP.getMsgType());
        String tplId = msgTempleteEnum.getTplId();
        String message = "";
        if (MsgTempleteEnum.AutoApprove.getTplId().equals(msgTempleteEnum.getTplId())){
            message = String.format(msgTempleteEnum.getTplValue(), sendShortMsgOP.getUserName());
        }else if (MsgTempleteEnum.ManApprove.getTplId().equals(msgTempleteEnum.getTplId())){
            message = String.format(msgTempleteEnum.getTplValue(), sendShortMsgOP.getUserName(),
					ChannelEnum.getAppByCode(sendShortMsgOP.getChannelId()),
					LoanProductEnum.getDesc(sendShortMsgOP.getProductId()));
        }else if (MsgTempleteEnum.Repay.getTplId().equals(msgTempleteEnum.getTplId())){
            message = String.format(msgTempleteEnum.getTplValue(), sendShortMsgOP.getUserName(),
					ChannelEnum.getAppByCode(sendShortMsgOP.getChannelId()),LoanProductEnum.getDesc(sendShortMsgOP.getProductId()),
                    sendShortMsgOP.getTerm(), sendShortMsgOP.getAmount());
        }
        sendShortMsgOP.setTplId(tplId);
        sendShortMsgOP.setMessage(message);

        this.sendMsgJuHe(sendShortMsgOP.getMobile(), sendShortMsgOP.getTplId(), sendShortMsgOP.getMessage());
        // 保存短信记录
        saveSmsLog(sendShortMsgOP, null);
    }

	@Override
	public void sendMsgJuHe(String mobile, String tplId, String tplValue) {
		String url = Global.getConfig("juhe.msgAPI.url");//请求接口地址
		String appKey = Global.getConfig("juhe.msgAPI.appKey");
		String userAgent =
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537" +
						".36";

		Map<String, String> params = new HashMap<>();
		params.put("mobile", mobile);//接收短信的手机号码
		params.put("tpl_id", tplId);//短信模板ID，请参考个人中心短信模板设置
		params.put("tpl_value", tplValue);//变量名和变量值对
		params.put("key", appKey);//应用APPKEY(应用详细页查询)

		Map<String, String> headers = new HashMap<>();
		headers.put("User-agent", userAgent);

		logger.debug("{}-{}-请求报文：{}","聚合数据","短信API服务",params);
		String responseStr = HttpUtils.getForJson(url, params, headers);
		logger.debug("{}-{}-应答结果：{}","聚合数据","短信API服务",responseStr);
	}

	@Override
	public TaskResult sendBindCardMsg() {
		logger.info("开始执行【绑卡通知】任务。");
		final AtomicInteger successNum = new AtomicInteger();
		final AtomicInteger totalNum = new AtomicInteger();
        long starTime = System.currentTimeMillis();
		try {
			List<LoanApply> list = loanApplyManager.getPassNoBindCardList();
            if (CollectionUtils.isEmpty(list)) {
                long endTime = System.currentTimeMillis();
                logger.info("执行【绑卡通知】任务结束，暂无数据。执行耗时{}", endTime - starTime);
                return new TaskResult(0, 0);
            }
			final Queue<LoanApply> queue = new ConcurrentLinkedDeque<>(list);
			final CountDownLatch countDownLatch = new CountDownLatch(list.size());
            for (int i = 0; i < list.size(); i++) {
                executorService.execute(new Runnable() {
					@Override
					public void run() {
						LoanApply poll = queue.poll();
						if (poll != null){
							System.out.println(Thread.currentThread().getName()+":"+poll);
							try {
								// 发短信
								SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
								sendShortMsgOP.setMsgType(2);// 终审通过
								sendShortMsgOP.setUserName(poll.getUserName());
								sendShortMsgOP.setMobile(poll.getMobile());
								sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
								sendShortMsgOP.setUserId(poll.getUserId());
								sendShortMsgOP.setChannelId(poll.getChannelId());
								sendShortMsgOP.setProductId(poll.getProductId());
								sendShortMsg(sendShortMsgOP);
								// 短信接口调用成功计数
								successNum.getAndAdd(1);
							} finally {
								countDownLatch.countDown();
								totalNum.getAndAdd(1);
							}
						}
					}
				});
            }
            countDownLatch.await();
		} catch (Exception e) {
			logger.error("----------执行【绑卡通知】定时任务异常----------", e);
		}
        long endTime = System.currentTimeMillis();
        logger.info("执行【绑卡通知】任务结束,执行耗时{}", endTime - starTime);
		return new TaskResult(successNum.intValue(), totalNum.intValue()-successNum.intValue());
	}

	@Override
    public TaskResult sendRepayNotice() {
        logger.info("开始执行【还款通知】任务。");
        long starTime = System.currentTimeMillis();
		final AtomicInteger successNum = new AtomicInteger();
		final AtomicInteger totalNum = new AtomicInteger();
        try {
            /** 查询第二天到期的还款计划明细 */
            List<RepayPlanItemDetail> itemList = repayPlanItemManager.noticeRepayList();
            if (CollectionUtils.isEmpty(itemList)) {
                long endTime = System.currentTimeMillis();
                logger.info("执行【还款通知】任务结束，暂无数据。执行耗时{}", endTime - starTime);
                return new TaskResult(0, 0);
            }

            final Queue<RepayPlanItemDetail> queue = new ConcurrentLinkedDeque<>(itemList);
			final CountDownLatch countDownLatch = new CountDownLatch(itemList.size());
            for (int i = 0; i < itemList.size(); i++) {
                executorService.execute(new Runnable() {
					@Override
					public void run() {
						RepayPlanItemDetail poll = queue.poll();
						if (poll != null){
							try {
								// 发短信
								SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
								sendShortMsgOP.setMsgType(3);// 还款提醒
								sendShortMsgOP.setUserName(poll.getUserName());
								sendShortMsgOP.setMobile(poll.getMobile());
								sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
								sendShortMsgOP.setUserId(poll.getUserId());
								sendShortMsgOP.setChannelId(poll.getChannelId());
								sendShortMsgOP.setProductId(poll.getProductId());
								sendShortMsgOP.setTerm("第"+poll.getThisTerm()+"期");
								if (poll.getTotalTerm() == 1){
									sendShortMsgOP.setTerm("");
								}
								sendShortMsgOP.setAmount(poll.getTotalAmount());
								sendShortMsg(sendShortMsgOP);
								// 短信接口调用成功计数
								successNum.getAndAdd(1);
							} finally {
								countDownLatch.countDown();
								totalNum.getAndAdd(1);
							}
						}
					}
				});
            }
			countDownLatch.await();
            // 保存通知
            //messageManager.insertBatch(list);
        } catch (Exception e) {
			logger.error("----------执行【还款通知】定时任务异常----------", e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("执行【还款通知】任务结束,执行耗时{}", endTime - starTime);
        return new TaskResult(successNum.intValue(), totalNum.intValue()-successNum.intValue());
    }

	@Override
	public boolean sendMsgKjcx(String phones, String msg) {
		boolean result = false;
		String account = "whjbdhy";
		String pwd = "kg8gg2d3";
		String seed = DateUtils.getHHmmss();
		Map<String, String> params = Maps.newHashMap();
		params.put("name", account);
		params.put("seed", seed);
		params.put("key", Digests.md5(Digests.md5(pwd).toLowerCase() + seed).toLowerCase());
		params.put("dest", phones);
		params.put("ext", "");
		params.put("reference", "");
		String xmlResult = "";
		try {
			params.put("content", "【聚宝钱包】" + msg + "退订回T");
			xmlResult = RestTemplateUtils.getInstance().postForJson(Global.MONGATE_SEND_SUBMIT_URL, params);
			/*
			 * String jsonResult = XMLUtil.xmlToJson(xmlResult); JSONObject json
			 * = JSONObject.parseObject(jsonResult); JSONObject responseJson =
			 * JSONObject.parseObject(json.getString("returnsms")); String
			 * returnData = responseJson.getString("returnstatus"); if
			 * (returnData.equals("Success")) { result = true; } else {
			 * logger.error("发送短信错误：" + xmlResult); }
			 */
			result = true;
			logger.info("空间畅想响应:" + xmlResult);
		} catch (Exception e) {
			logger.error("空间畅想发送短信出现异常：" + e.getMessage());
		}
		return result;
	}

}

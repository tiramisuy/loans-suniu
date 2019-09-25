package com.rongdu.loans.pay.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.service.AlarmService;
import com.rongdu.loans.credit.common.CreditApiVo;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.baofoo.agreement.rsa.SignatureUtils;
import com.rongdu.loans.pay.baofoo.agreement.util.FormatUtil;
import com.rongdu.loans.pay.baofoo.agreement.util.Log;
import com.rongdu.loans.pay.baofoo.agreement.util.SecurityUtil;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.baofoo.util.HttpUtil;
import com.rongdu.loans.pay.common.RespInfo;
import com.rongdu.loans.pay.message.BaofooRequest;
import com.rongdu.loans.pay.message.BaseDataContent;
import com.rongdu.loans.pay.message.BindCardDataContent;
import com.rongdu.loans.pay.message.BindCardResponse;
import com.rongdu.loans.pay.message.ConfirmAuthPayDataContent;
import com.rongdu.loans.pay.message.ConfirmAuthPayResponse;
import com.rongdu.loans.pay.message.ConfirmBindCardDataContent;
import com.rongdu.loans.pay.message.PreAuthPayDataContent;
import com.rongdu.loans.pay.message.PreAuthPayResponse;
import com.rongdu.loans.pay.op.AuthPayQueryOP;
import com.rongdu.loans.pay.op.ConfirmAuthPayOP;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.PreAuthPayOP;
import com.rongdu.loans.pay.service.BaofooAuthPayService;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.service.PartnerApiService;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.BaofooFeeUtils;
import com.rongdu.loans.pay.vo.AuthPayQueryResultVO;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.PreAuthPayVO;

/**
 * 认证支付
 * @author Administrator
 *
 */
@Service(value = "baofooAuthPayService")
public class BaofooAuthPayServiceImpl extends PartnerApiService implements BaofooAuthPayService {

	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private CustUserService userService;
	@Autowired
	private BindCardService bindCardService;
	@Autowired
	private BaofooWithdrawService baofooWithdrawService;
	@Autowired
	private AlarmService alarmService;
	// 商户号
	private String memberId = BaofooConfig.member_id;
	// 终端号
	private String terminalId = BaofooConfig.authpay_terminal_id;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 11-预绑卡交易
	 * 
	 * @param param
	 * @return
	 */
	public BindCardResultVO preBindCard(DirectBindCardOP param) {
		logger.debug("========11-预绑卡类交易===========");
		// 交易子类
		String txnSubType = "11";
		String transId = "AP" + txnSubType + System.nanoTime();

		BindCardDataContent content = new BindCardDataContent();
		content.setTxn_sub_type(txnSubType);

		content.setAcc_no(param.getCardNo());
		content.setTrans_id(transId);
		content.setId_card(param.getIdNo());
		content.setId_holder(param.getRealName());
		content.setMobile(param.getMobile());
		content.setPay_code(param.getBankCode());
		content.setTerminal_id(BaofooConfig.authpay_terminal_id);

		// 配置参数
		String partnerId = BaofooConfig.partner_id;
		String partnerName = BaofooConfig.partner_name;
		String bizCode = BaofooConfig.pre_bind_card_biz_code;
		String bizName = BaofooConfig.pre_bind_card_biz_name;

		BindCardResponse response = (BindCardResponse) sendRequest(content, BindCardResponse.class, partnerId,
				partnerName, bizCode, bizName);
		BindCardResultVO vo = new BindCardResultVO();
		if (response != null && response.isSuccess()) {
			vo.setSuccess(response.isSuccess());
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setOrderNo(response.getTrans_id());
		}
		// 预绑卡后，保存绑卡信息
		saveBindInfo(param, content, response);
		return vo;
	}

	/**
	 * 保存绑卡信息
	 * 
	 * @param param
	 * @param content
	 * @param response
	 * @return
	 */
	@Transactional
	private BindCardVO saveBindInfo(DirectBindCardOP param, BindCardDataContent content, BindCardResponse response) {
		BindCardVO bindInfo = null;
		String id = generateBindCardId(param.getIdNo(), param.getCardNo(), param.getMobile());
		bindInfo = bindCardService.get(id);
		// 预绑卡时，创建绑卡信息
		if (bindInfo == null) {
			bindInfo = new BindCardVO();
			bindInfo.setIsNewRecord(true);
			bindInfo.setId(id);
		}
		bindInfo.setUserId(param.getUserId());
		bindInfo.setCardNo(param.getCardNo());
		String bankCode = param.getBankCode();
		String bankName = BankLimitUtils.getNameByBankCode(bankCode);
		bindInfo.setBankCode(bankCode);
		bindInfo.setBankName(bankName);
		bindInfo.setSource(param.getSource());
		bindInfo.setIdNo(param.getIdNo());
		bindInfo.setName(param.getRealName());
		bindInfo.setMobile(param.getMobile());
		bindInfo.setTxTime(new Date());
		bindInfo.setTxType("direct");
		bindInfo.setChlCode("BAOFOO");
		bindInfo.setChlName("宝付支付");
		bindInfo.setTxDate(DateUtils.getDate("yyyyMMdd"));
		bindInfo.setIp(param.getIpAddr());
		bindInfo.setChlOrderNo(content.getTrans_id());
		// 预绑卡时，保存绑卡信息
		if (bindInfo.getIsNewRecord()) {
			bindInfo.setStatus(response.getCode());
			bindInfo.setRemark(response.getMsg());
			bindCardService.save(bindInfo);
			logger.info("认证支付-绑定银行卡-记录绑卡信息：{},{},{},{}", param.getUserId(), bindInfo.getBankName(), param.getCardNo(),
					param.getMobile());
		} else {
			// 直接绑卡时，更新绑卡信息
			if (response.isSuccess()) {
				// 直接绑卡，成功
				bindInfo.setBindId(response.getBind_id());
				bindInfo.setStatus(ErrInfo.SUCCESS.getCode());
				bindInfo.setRemark(response.getMsg());
			} else {
				// 直接绑卡，失败
				bindInfo.setStatus(response.getCode());
				bindInfo.setRemark(response.getMsg());
			}
			logger.info("认证支付-绑定银行卡-更新绑卡信息：{},{},{},{},{}", bindInfo.getStatus(), param.getUserId(),
					bindInfo.getBankName(), param.getCardNo(), param.getMobile());
			bindCardService.update(bindInfo);
		}
		return bindInfo;

	}

	/**
	 * 保存协议支付绑卡信息
	 * 
	 * @param param
	 * @param transId
	 * @param bindId
	 * @param code
	 * @param msg
	 * @return
	 */
	@Transactional
	private BindCardVO saveBindInfo(DirectBindCardOP param, String transId, String bindId, String code, String msg) {
		BindCardVO bindInfo = null;
		String id = generateBindCardId(param.getIdNo(), param.getCardNo(), param.getMobile());
		bindInfo = bindCardService.get(id);
		// 预绑卡时，创建绑卡信息
		if (bindInfo == null) {
			bindInfo = new BindCardVO();
			bindInfo.setIsNewRecord(true);
			bindInfo.setId(id);
		}
		bindInfo.setUserId(param.getUserId());
		bindInfo.setCardNo(param.getCardNo());
		String bankCode = param.getBankCode();
		String bankName = BankLimitUtils.getNameByBankCode(bankCode);
		bindInfo.setBankCode(bankCode);
		bindInfo.setBankName(bankName);
		bindInfo.setSource(param.getSource());
		bindInfo.setIdNo(param.getIdNo());
		bindInfo.setName(param.getRealName());
		bindInfo.setMobile(param.getMobile());
		bindInfo.setTxTime(new Date());
		bindInfo.setTxType("agree");
		bindInfo.setChlCode("BAOFOO");
		bindInfo.setChlName("宝付预绑卡");
		bindInfo.setTxDate(DateUtils.getDate("yyyyMMdd"));
		bindInfo.setIp(param.getIpAddr());
		bindInfo.setChlOrderNo(transId);
		bindInfo.setStatus(code);
		bindInfo.setRemark(msg);
		bindInfo.setBindId(bindId);
		// 预绑卡时，保存绑卡信息
		if (bindInfo.getIsNewRecord()) {
			bindCardService.save(bindInfo);
			logger.info("协议支付-预绑卡-记录绑卡信息：{},{},{},{}", param.getUserId(), bindInfo.getBankName(), param.getCardNo(),
					param.getMobile());
		} else {
			logger.info("协议支付-预绑卡-更新绑卡信息：{},{},{},{},{}", msg, param.getUserId(),
					bindInfo.getBankName(), param.getCardNo(), param.getMobile());
			bindCardService.update(bindInfo);
		}
		return bindInfo;

	}

	private Object sendRequest(BaseDataContent content, Class<?> clazz, String partnerId, String partnerName,
			String bizCode, String bizName) {
		// 测试地址
		String url = BaofooConfig.auth_pay_url;
		// 商户私钥
		String pfxpath = this.getClass().getResource(BaofooConfig.keystore_path).getPath();
		// 宝付公钥
		String cerpath = this.getClass().getResource(BaofooConfig.pubkey_path).getPath();
		BaofooRequest request = new BaofooRequest();
		request.setTxn_sub_type(content.getTxn_sub_type());
		String reqString = "";
		reqString = JsonMapper.toJsonString(content);
		long start = System.currentTimeMillis();
		logger.debug("{}-{}-请求报文：{}", partnerName, bizName, reqString);
		String base64str = null;
		Map<String, String> params = null;
		String respString = null;
		try {
			base64str = SecurityUtil.Base64Encode(reqString);
			String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, BaofooConfig.keystore_password);
			request.setData_content(dataContent);
			params = BeanUtils.describe(request);

			logger.debug("{}-{}-请求地址：{}", partnerName, bizName, url);
			// logger.debug("{}-{}-请求报文：{}",partnerName,bizName,params);
			respString = httpUtils.getForJson(url, params);
			// logger.debug("{}-{}-应答结果",partnerName,bizName);
			respString = RsaCodingUtil.decryptByPubCerFile(respString, cerpath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("绑卡请求报文出现异常：" + e1.getMessage());
		}
		// catch (InvocationTargetException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// logger.debug("绑卡出现异常：" + e1.getMessage());
		// } catch (NoSuchMethodException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// logger.debug("绑卡出现异常：" + e1.getMessage());
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// logger.debug("绑卡出现异常：" + e1.getMessage());
		// }

		// 判断解密是否正确。如果为空则宝付公钥不正确
		if (respString.isEmpty()) {
			logger.debug("认证支付-{}-检查解密公钥是否正确！", getSubTypeMap().get(request.getTxn_sub_type()));
		}
		try {
			respString = SecurityUtil.Base64Decode(respString);
			logger.debug("{}-{}-应答结果：{}", partnerName, bizName, respString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("绑卡检查解密公钥异常：" + e.getMessage());
		}

		// 将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(respString, clazz);
		CreditApiVo vo = (CreditApiVo) response;
		boolean success = vo.isSuccess();
		String code = vo.getCode();
		String msg = vo.getMsg();
		long end = System.currentTimeMillis();
		long timeCost = end - start;
		saveApiInvokeLog(partnerId, partnerName, bizCode, bizName, timeCost, success, code, msg);
		// logger.debug("请求结束，{}-{}-耗时：{}ms",partnerName,bizName,timeCost);

		return response;
	}

	private String generateBindCardId(String idCard, String accNo, String mobile) {
		String input = idCard + accNo + mobile;
		return Digests.md5(input);
	}

	@Transactional
	public BindCardResultVO confirmBindCard(ConfirmBindCardOP param) {
		logger.debug("========12-确认绑卡交易===========");
		// 交易子类
		String txnSubType = "12";
		ConfirmBindCardDataContent content = new ConfirmBindCardDataContent();
		content.setTxn_sub_type(txnSubType);
		// 短信验证码
		content.setSms_code(param.getMsgVerCode());
		// 预绑卡订单号
		content.setTrans_id(param.getOrderNo());
		content.setTerminal_id(BaofooConfig.authpay_terminal_id);
		// 配置参数
		String partnerId = BaofooConfig.partner_id;
		String partnerName = BaofooConfig.partner_name;
		String bizCode = BaofooConfig.confirm_bind_card_biz_code;
		String bizName = BaofooConfig.confirm_bind_card_biz_name;

		BindCardResponse response = (BindCardResponse) sendRequest(content, BindCardResponse.class, partnerId,
				partnerName, bizCode, bizName);
		BindCardResultVO vo = new BindCardResultVO();
		if (response != null && response.isSuccess()) {
			vo.setSuccess(response.isSuccess());
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setBindId(response.getBind_id());
		}

		int updateNum = 0;
		BindCardVO bindInfo = bindCardService.findByOrderNo(param.getOrderNo());
		if (bindInfo != null) {
			if (response.isSuccess()) {
				CustUserVO user = userService.getCustUserById(bindInfo.getUserId());
				bindInfo.setBindId(response.getBind_id());
				bindInfo.setStatus(ErrInfo.SUCCESS.getCode());
				bindInfo.setRemark(response.getMsg());
				user.setBindId(response.getBind_id());
				updateUser(user);
			} else {
				bindInfo.setStatus(response.getCode());
				bindInfo.setRemark(response.getMsg());
			}
			updateNum = bindCardService.update(bindInfo);
		}
		return vo;
	}

	private void updateUser(CustUserVO user) {
		int userNum = userService.updateBindInfo(user);
		logger.info("认证支付-绑卡成功-更新绑定关系及预留手机号：{},{},{}", user.getName(), user.getMobile(), userNum);
	}

	@Transactional
	public BindCardResultVO directBindCard(DirectBindCardOP param) {

		BindCardResultVO vo = new BindCardResultVO();
		/*
		 * 先查询本地是否绑定其他银行卡，如果有绑定其它卡， 则解绑成功之后再绑定新卡，如果没有绑定或者已绑定相同卡号， 则不绑卡
		 */
		CustUserVO userVo = userService.getCustUserById(param.getUserId());
		if (userVo != null && !param.getCardNo().equals(userVo.getCardNo()) && !StringUtils.isBlank(userVo.getBindId())) {
			// 解绑旧卡
			RespInfo cancleInfo = cancelBind(userVo, userVo.getBindId());
			if (!"0000".equals(cancleInfo.getCode())) {
				// 解绑失败
				vo.setSuccess(false);
				vo.setCode(cancleInfo.getCode());
				vo.setMsg("解绑失败，此次绑卡不成功！");
				// logger.debug("解绑失败,错误信息{}",cancleInfo.getMsg());
				// TODO 解绑失败，还是继续绑定
				// return vo;
			}
		} else if (userVo != null && param.getCardNo().equals(userVo.getCardNo())) {
			vo.setSuccess(true);
			vo.setCode("0000");
			vo.setBindId(userVo.getBindId());
			vo.setMsg("该卡已经绑定，不需要重新绑定！");
			logger.debug("该卡已经绑定，不需要重新绑定！");
			return vo;
		}

		// logger.debug("========01-直接绑卡交易===========");
		CustUserVO user = new CustUserVO();
		user.setId(param.getUserId());
		user.setRealName(param.getRealName());
		user.setIdNo(param.getIdNo());
		user.setMobile(param.getMobile());
		user.setBankCode(param.getBankCode());
		user.setCardNo(param.getCardNo());
		user.setBankCityId(param.getCityId());
		// 交易子类
		String txnSubType = "01";
		// 商户订单号
		String transId = "AP" + txnSubType + System.nanoTime();
		BindCardDataContent content = new BindCardDataContent();
		content.setTxn_sub_type(txnSubType);
		content.setAcc_no(user.getCardNo());
		content.setTrans_id(transId);
		content.setId_card(user.getIdNo());
		content.setId_holder(user.getRealName());
		content.setMobile(user.getMobile());
		content.setPay_code(user.getBankCode());
		content.setTerminal_id(BaofooConfig.authpay_terminal_id);

		// 配置参数
		String partnerId = BaofooConfig.partner_id;
		String partnerName = BaofooConfig.partner_name;
		String bizCode = BaofooConfig.direct_bind_card_biz_code;
		String bizName = BaofooConfig.direct_bind_card_biz_name;

		// TODO 绑卡接口请求暂时注掉
		BindCardResponse response = (BindCardResponse) sendRequest(content, BindCardResponse.class, partnerId,
				partnerName, bizCode, bizName);
		// BindCardResponse response = new BindCardResponse();
		// response.setBind_id(null);
		// response.setSuccess(true);
		// response.setResp_code("0000");
		// response.setMsg("绑卡成功！");

		if (response != null) {
			if (response.isSuccess()) {
				vo.setSuccess(response.isSuccess());
				vo.setCode(response.getCode());
				vo.setMsg(response.getMsg());
				vo.setBindId(response.getBind_id());
				user.setBindId(response.getBind_id());
				// 更新用户绑卡信息
				userService.updateBindInfo(user);
				// 更新用户信息后清除用户缓存
				JedisUtils.delObject(Global.USER_CACHE_PREFIX + param.getUserId());
				// logger.debug("绑卡返回成功，更新用户绑卡信息成功:{}", user);
			} else {
				vo.setSuccess(response.isSuccess());
				vo.setCode(response.getCode());
				vo.setMsg(response.getMsg());
				// logger.debug("绑卡返回失败，返回绑卡信息:{}", response);
			}
			// 直接绑卡后，保存绑卡信息
			saveBindInfo(param, content, response);
		}
		logger.debug("绑卡结束，返回绑卡信息:{},{}", param, response);
		return vo;
	}

	private boolean isRespSuccess(Map<String, String> resp) {
		return StringUtils.isNotBlank(resp.get("resp_code")) && resp.get("resp_code").equals("0000");
	}

	@Transactional
	public RespInfo cancelBind(CustUserVO user, String bindId) {
		// logger.debug("========02-解除绑定关系交易===========");
		RespInfo respInfo = new RespInfo();
		String txn_sub_type = "02";
		// 商户订单号
		String transId = "AP" + txn_sub_type + System.nanoTime();
		Map<String, String> params = createParams(txn_sub_type);
		Map<String, Object> encryptParams = createEncryptParams(params);
		// 获取绑定标识
		encryptParams.put("bind_id", bindId);
		Map<String, String> resp = sendRequest(params, encryptParams);
		respInfo.setCode(resp.get("resp_code"));
		respInfo.setMsg(resp.get("resp_msg"));
		if (isRespSuccess(resp)) {
			// BindCardVO bindInfo = new BindCardVO();
			// bindInfo.setBindId(bindId);
			// bindInfo.setUserId(user.getId());
			// int bindNum = payBindInfoService.cancelCurrentBindInfo(bindInfo);
			// logger.info("认证支付-解绑银行卡-更新用户绑定关系：{},{}",bindId,user.getId());
			user.setBankCode(null);
			user.setBindId(null);
			user.setMobile(null);
			user.setCardNo(null);
			int userNum = userService.updateBindInfo(user);
			// logger.info("认证支付-解绑银行卡-更新用户绑定关系：{},{},{}",user.getId(),user.getName(),
			// userNum);
		}
		logger.info("认证支付-解绑银行卡-返回解绑信息：{},{},{}", user.getId(), user.getName(), resp);
		return respInfo;
	}

	@Transactional
	public void cancelBindOldCard(String bindId) {
		String txn_sub_type = "02";
		Map<String, String> params = createParams(txn_sub_type);
		Map<String, Object> encryptParams = createEncryptParams(params);
		// 获取绑定标识
		encryptParams.put("bind_id", bindId);
		Map<String, String> resp = sendRequest(params, encryptParams);
		logger.info("认证支付-解绑旧卡-返回解绑信息：{}", resp);
	}

	public RespInfo queryBind(String accNo) {
		logger.debug("========03-查询绑定关系交易===========");
		String txn_sub_type = "03";
		// 商户订单号
		String transId = "AP" + txn_sub_type + System.nanoTime();
		Map<String, String> params = createParams(txn_sub_type);
		Map<String, Object> encryptParams = createEncryptParams(params);
		// 银行卡号
		encryptParams.put("acc_no", accNo);
		Map<String, String> resp = sendRequest(params, encryptParams);
		RespInfo respInfo = new RespInfo();
		respInfo.setMsg(resp.get("resp_code"));
		respInfo.setMsg(resp.get("resp_msg"));
		if (isRespSuccess(resp)) {
			respInfo.put("bindId", resp.get("bind_id"));
			respInfo.put("payCardType", resp.get("pay_card_type"));
			respInfo.put("addditionalInfo", resp.get("addditional_info"));
		}
		return respInfo;
	}

	public PreAuthPayVO preAuthPay(PreAuthPayOP param) {
		logger.debug("========15-认证支付类预支付交易===========");
		String txnSubType = "15";
		CustUserVO user = userService.getCustUserById(param.getUserId());
		// 商户订单号
		String orderNo = "AP" + txnSubType + System.nanoTime();

		// 金额以分为单位(整型数据)并把元转换成分
		String txnAmtFen = MoneyUtils.yuan2fen(param.getTxAmt());
		// 获取绑定标识
		Map<String, String> riskContent = new HashMap<String, String>();
		riskContent.put("client_ip", param.getIpAddr());
		PreAuthPayDataContent content = new PreAuthPayDataContent();
		content.setTxn_sub_type(txnSubType);
		content.setBind_id(user.getBindId());
		content.setTxn_amt(txnAmtFen);
		content.setRisk_content(riskContent);
		content.setTrans_id(orderNo);
		content.setTerminal_id(BaofooConfig.authpay_terminal_id);

		// 配置参数
		String partnerId = BaofooConfig.partner_id;
		String partnerName = BaofooConfig.partner_name;
		String bizCode = BaofooConfig.pre_auth_pay_biz_code;
		String bizName = BaofooConfig.pre_auth_pay_biz_name;

		PreAuthPayResponse response = (PreAuthPayResponse) sendRequest(content, PreAuthPayResponse.class, partnerId,
				partnerName, bizCode, bizName);
		PreAuthPayVO vo = new PreAuthPayVO();
		if (response != null) {
			vo.setSuccess(response.isSuccess());
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setChlOrderNo(response.getBusiness_no());
		}
		saveRepayLog(user, param, content, response);
		return vo;
	}

	/**
	 * 保存支付订单
	 * 
	 * @param user
	 * @param param
	 * @param content
	 * @param response
	 */
	@Transactional
	private void saveRepayLog(CustUserVO user, PreAuthPayOP param, PreAuthPayDataContent content,
			PreAuthPayResponse response) {
		String orderNo = content.getTrans_id();
		String txnAmtYuan = param.getTxAmt();
		RepayLogVO order = new RepayLogVO();
		order.setId(orderNo);
		order.setNewRecord(true);
		order.setUserId(user.getId());
		order.setApplyId(param.getApplyId());
		order.setContractId(param.getContractId());
		order.setRepayPlanItemId(param.getRepayPlanItemId());
		order.setPayType(param.getPayType());
//		BindCardVO bindCardVO = bindCardService.findByBindId(user.getBindId());
//		if (bindCardVO != null) {
//			order.setUserName(bindCardVO.getName());
//			order.setMobile(bindCardVO.getMobile());
//			order.setIdNo(bindCardVO.getIdNo());
//			order.setBankCode(bindCardVO.getBankCode());
//			order.setBankName(bindCardVO.getBankName());
//			order.setCardNo(bindCardVO.getCardNo());
//		}
		order.setUserName(user.getRealName());
		order.setIdNo(user.getIdNo());
		order.setMobile(user.getMobile());
		order.setBankCode(user.getBankCode());
		order.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
		order.setCardNo(user.getCardNo());
		order.setBindId(user.getBindId());

		order.setTerminal(param.getSource());
		order.setIp(param.getIpAddr());
		order.setTxType("AUTH_PAY");
		order.setChlCode("BAOFOO");
		order.setChlName("宝付支付");
		order.setGoodsName("聚宝钱包还款");
		order.setGoodsNum(1);
		String txFee = BaofooFeeUtils.computeAuthpayFee(txnAmtYuan);
		order.setTxAmt(MoneyUtils.toDecimal(txnAmtYuan));
		order.setTxFee(MoneyUtils.toDecimal(txFee));
		order.setTxTime(new Date());
		order.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		order.setStatus(response.getCode());
		order.setRemark("预支付：" + response.getMsg());
		// 宝付业务流水号,用于在后续类交易中唯一标识一笔交易
		order.setChlOrderNo(response.getBusiness_no());
		logger.debug("认证支付-正在保存支付订单：{}，{}元，{}", user.getRealName(), txnAmtYuan, orderNo);
		repayLogService.save(order);
	}

	@Transactional
	public ConfirmAuthPayVO confirmAuthPay(ConfirmAuthPayOP param) {
		logger.debug("========16-认证支付类支付确认交易===========");
		String txnSubType = "16";
		// 商户订单号
		String transId = "AP" + txnSubType + System.nanoTime();
		ConfirmAuthPayDataContent content = new ConfirmAuthPayDataContent();
		content.setTxn_sub_type(txnSubType);
		// 支付短信验证码
		content.setSms_code(param.getSmsCode());
		// 宝付业务流水号
		content.setBusiness_no(param.getChlOrderNo());
		content.setTerminal_id(BaofooConfig.authpay_terminal_id);

		// 配置参数
		String partnerId = BaofooConfig.partner_id;
		String partnerName = BaofooConfig.partner_name;
		String bizCode = BaofooConfig.confirm_auth_pay_biz_code;
		String bizName = BaofooConfig.confirm_auth_pay_biz_name;

		ConfirmAuthPayVO vo = new ConfirmAuthPayVO();
		RepayLogVO order = repayLogService.findByChlOrderNo(param.getChlOrderNo());
		// 订单存在
		if (order != null) {
			String succCode = ErrInfo.SUCCESS.getCode();
			// 订单已经支付成功
			if (StringUtils.equals(succCode, order.getStatus())) {
				vo.setCode(ErrInfo.ERROR.getCode());
				vo.setMsg("该订单已经支付成功，请勿重复付款");
			} else {
				// 订单尚未支付成功
				ConfirmAuthPayResponse response = (ConfirmAuthPayResponse) sendRequest(content,
						ConfirmAuthPayResponse.class, partnerId, partnerName, bizCode, bizName);
				if (response != null) {
					vo.setCode(response.getCode());
					vo.setMsg(response.getMsg());

					order.setStatus(response.getCode());
					order.setRemark(response.getMsg());
					if (!response.isSuccess()) {
						repayLogService.update(order);
					} else {
						// 悲观锁，只有未成功的订单，才能继续处理
						String succAmtFen = response.getSucc_amt();
						String succAmtYuan = MoneyUtils.fen2yuan(succAmtFen);
						String succTime = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
						vo.setSuccess(response.isSuccess());
						vo.setSuccAmt(succAmtYuan);
						vo.setSuccTime(succTime);
						order.setSuccTime(new java.sql.Date(System.currentTimeMillis()));
						order.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
						order.setStatus(succCode);
						// 利用乐观锁，避免重复处理订单
						int updateRows = repayLogService.updateRepayResult(order);

						if (1 == updateRows) {
							// 支付成功不处理，等待定时任务执行
							// baofooWithdrawService.withraw(order);
						} else {
							logger.info("宝付支付-认证支付-支付成功同步回调，收到通知，但未更新本地订单状态");
						}
					}

				} else {
					logger.error("宝付支付-认证支付-支付异常：{}，{}", order.getUserName(), order.getTxAmt());
					String mobiles = BaofooConfig.alarm_mobiles;
					String message = String.format("支付异常提醒：%s正在还款%s元，遇到异常", order.getUserName(), order.getTxAmt());
					alarmService.sendAlarmSms(mobiles, message);
				}

			}

		} else {
			logger.error("认证支付-本地掉单，宝付交易流水号：{}", param.getChlOrderNo());
			vo.setCode(ErrInfo.ERROR.getCode());
			vo.setMsg("该支付订单不存在");
		}

		return vo;
	}

	public RespInfo queryAuthPayStatus(String orderNo) {
		logger.debug("========06/31-支付交易状态查询交易===========");
		String txn_sub_type = "06";
		Map<String, String> params = createParams(txn_sub_type);
		Map<String, Object> encryptParams = createEncryptParams(params);
		// 原始商户订单号
		encryptParams.put("orig_trans_id", orderNo);
		Map<String, String> resp = sendRequest(params, encryptParams);
		RespInfo respInfo = new RespInfo();
		// if(isRespSuccess(resp)){
		// respInfo.setMsg(resp.get("resp_msg"));
		// }else{
		respInfo.setCode(resp.get("resp_code"));
		respInfo.setMsg(resp.get("resp_msg"));
		// }
		respInfo.put("origTransId", resp.get("orig_trans_id"));
		respInfo.put("transNo", resp.get("trans_no"));
		respInfo.put("succAmt", resp.get("succ_amt"));
		respInfo.put("orderStat", resp.get("order_stat"));
		return respInfo;

	}

	public AuthPayQueryResultVO queryAuthPayResult(AuthPayQueryOP op) {
		logger.debug("========31-支付交易状态查询交易===========");
		String txn_sub_type = "31";
		Map<String, String> params = createParams(txn_sub_type);
		Map<String, Object> encryptParams = createEncryptParams(params);
		// 原始商户订单号
		encryptParams.put("orig_trans_id", op.getOrigTransId());
		encryptParams.put("orig_trade_date", op.getOrigTradeDate());
		Map<String, String> resp = sendRequest(params, encryptParams);
		AuthPayQueryResultVO result = null;
		if (resp != null) {
			result = new AuthPayQueryResultVO();
			result.setCode(resp.get("resp_code"));
			result.setMsg(resp.get("resp_msg"));
			result.setOrigTransId(resp.get("orig_trans_id"));
			result.setTransNo(resp.get("trans_no"));
			result.setSuccAmt(resp.get("succ_amt"));
			result.setOrderStat(resp.get("order_stat"));
			return result;
		}
		return null;
	}

	private Map<String, String> sendRequest(Map<String, String> params, Map<String, Object> encryptParams) {
		// 测试地址
		String request_url = BaofooConfig.auth_pay_url;
		// 商户私钥
		String pfxpath = this.getClass().getResource(BaofooConfig.keystore_path).getPath();
		// 宝付公钥
		String cerpath = this.getClass().getResource(BaofooConfig.pubkey_path).getPath();
		String reqString = "";
		reqString = JsonMapper.toJsonString(encryptParams);
		logger.debug("认证支付-{}-请求参数：{}", getSubTypeMap().get(params.get("txn_sub_type")), reqString);
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(reqString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, BaofooConfig.keystore_password);
		params.put("data_content", data_content);
		String respString = HttpUtil.RequestForm(request_url, params);
		logger.debug("认证支付-{}-应答结果：{}", getSubTypeMap().get(params.get("txn_sub_type")), respString);
		respString = RsaCodingUtil.decryptByPubCerFile(respString, cerpath);
		// 判断解密是否正确。如果为空则宝付公钥不正确
		if (respString.isEmpty()) {
			logger.debug("认证支付-{}-检查解密公钥是否正确！", getSubTypeMap().get(params.get("txn_sub_type")));
		}
		try {
			respString = SecurityUtil.Base64Decode(respString);
			logger.debug("认证支付-{}-应答结果（解密）：{}", getSubTypeMap().get(params.get("txn_sub_type")), respString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将JSON转化为Map对象
		Map<String, String> resp = null;
		resp = (Map<String, String>) JsonMapper.fromJsonString(respString, HashMap.class);
		logger.debug("认证支付-{}-应答代码：{}，应答消息:{}", getSubTypeMap().get(params.get("txn_sub_type")), resp.get("resp_code"),
				resp.get("resp_msg"));
		return resp;
	}

	private Map<String, Object> createEncryptParams(Map<String, String> params) {
		/**
		 * 报文体
		 */
		Map<String, Object> encryptParams = new HashMap<String, Object>();

		/**
		 * 公共参数
		 */
		// 交易日期
		String trade_date = DateUtils.getDate("yyyyMMddHHmmss");
		encryptParams.put("txn_sub_type", params.get("txn_sub_type"));
		encryptParams.put("biz_type", "0000");
		encryptParams.put("terminal_id", terminalId);
		encryptParams.put("member_id", memberId);
		encryptParams.put("trans_serial_no", "SN" + System.nanoTime());
		encryptParams.put("trade_date", trade_date);
		// 附加信息
		encryptParams.put("additional_info", "");
		// 保留信息
		encryptParams.put("req_reserved", "");
		return encryptParams;
	}

	private Map<String, String> createParams(String txnSubType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", "4.0.0.0");
		params.put("member_id", memberId);
		params.put("terminal_id", terminalId);
		params.put("txn_type", "0431");
		params.put("txn_sub_type", txnSubType);
		params.put("data_type", "json");
		return params;
	}

	private static Map<String, String> getSubTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "预绑卡交易");
		map.put("12", "确认绑卡交易");
		map.put("01", "直接绑卡交易");
		map.put("02", "解除绑定关系交易");
		map.put("03", "查询绑定关系交易");
		map.put("15", "认证支付类预支付交易");
		map.put("16", "认证支付类支付确认交易");
		map.put("31", "交易状态查询交易");
		return map;
	}

	@Override
	public BindCardResultVO reBindCard(DirectBindCardOP param) {

		BindCardResultVO vo = new BindCardResultVO();
		CustUserVO userVo = userService.getCustUserById(param.getUserId());
		if (userVo != null && param.getCardNo().equals(userVo.getCardNo())) {
			vo.setSuccess(true);
			vo.setCode("0000");
			vo.setBindId(userVo.getBindId());
			vo.setMsg("该卡已经绑定，不需要重新绑定！");
			logger.debug("该卡已经绑定，不需要重新绑定！");
			return vo;
		}

		String oldBindId = userVo.getBindId();// 旧卡绑定Id
		userVo.setBankCode(param.getBankCode());
		userVo.setCardNo(param.getCardNo());
		// 交易子类
		String txnSubType = "01";
		// 商户订单号
		String transId = "AP" + txnSubType + System.nanoTime();
		BindCardDataContent content = new BindCardDataContent();
		content.setTxn_sub_type(txnSubType);
		content.setAcc_no(userVo.getCardNo());
		content.setTrans_id(transId);
		content.setId_card(userVo.getIdNo());
		content.setId_holder(userVo.getRealName());
		content.setMobile(userVo.getMobile());
		content.setPay_code(userVo.getBankCode());
		content.setTerminal_id(BaofooConfig.authpay_terminal_id);

		// 配置参数
		String partnerId = BaofooConfig.partner_id;
		String partnerName = BaofooConfig.partner_name;
		String bizCode = BaofooConfig.direct_bind_card_biz_code;
		String bizName = BaofooConfig.direct_bind_card_biz_name;
		BindCardResponse response = (BindCardResponse) sendRequest(content, BindCardResponse.class, partnerId,
				partnerName, bizCode, bizName);

		if (response != null) {
			if (response.isSuccess()) {
				vo.setSuccess(response.isSuccess());
				vo.setCode(response.getCode());
				vo.setMsg(response.getMsg());
				vo.setBindId(response.getBind_id());
				userVo.setBindId(response.getBind_id());
				// 更新用户绑卡信息
				userService.updateBindInfo(userVo);
				// 更新用户信息后清除用户缓存
				JedisUtils.delObject(Global.USER_CACHE_PREFIX + param.getUserId());
				// 解绑旧卡
				cancelBindOldCard(oldBindId);
			} else {
				vo.setSuccess(response.isSuccess());
				vo.setCode(response.getCode());
				vo.setMsg(response.getMsg());
			}
			// 直接绑卡后，保存绑卡信息
			saveBindInfo(param, content, response);
		}
		logger.debug("绑卡结束，返回绑卡信息:{},{}", param, response);
		return vo;
	}
}

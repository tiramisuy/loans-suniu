/**
 * ©2017 聚宝钱包 All rights reserved
 */
package com.rongdu.loans.pay.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

import com.rongdu.loans.pay.baofoo.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.BizException;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.service.AlarmService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.loan.service.CarefreeCounterfoilService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.PactRecord;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.baofoo.demo.base.TransContent;
import com.rongdu.loans.pay.baofoo.demo.base.TransHead;
import com.rongdu.loans.pay.baofoo.demo.base.request.TransReqBF0040001;
import com.rongdu.loans.pay.baofoo.demo.base.request.TransReqBF0040002;
import com.rongdu.loans.pay.baofoo.demo.base.response.TransRespBF0040001;
import com.rongdu.loans.pay.baofoo.demo.base.response.TransRespBF0040002;
import com.rongdu.loans.pay.baofoo.domain.RequestParams;
import com.rongdu.loans.pay.baofoo.http.SimpleHttpResponse;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.baofoo.util.BaofooClient;
import com.rongdu.loans.pay.baofoo.util.SecurityUtil;
import com.rongdu.loans.pay.baofoo.util.TransConstant;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.BaofooFeeUtils;
import com.rongdu.loans.pay.utils.WithdrawErrInfo;

/**
 * 宝付代付提现
 * 
 * @author sunda
 */
@Service("baofooWithdrawService")
public class BaofooWithdrawServiceImpl extends BaseService implements BaofooWithdrawService {

	@Autowired
	private PayLogService payLogService;
	@Autowired
	private CustUserService userService;
	// @Autowired
	// private RepayLogService repayLogService;
	@Autowired
	private AlarmService alarmService;
	@Autowired
	private CarefreeCounterfoilService carefreeCounterfoilService;

	public static final List<String> BAOFOO_PAY_UNSOLVED_STATUS = new ArrayList<String>();
	public static final List<String> BAOFOO_PAY_SUCCESS_STATUS = new ArrayList<String>();
	static {
		BAOFOO_PAY_UNSOLVED_STATUS.add(WithdrawErrInfo.P.getCode());
		BAOFOO_PAY_UNSOLVED_STATUS.add(WithdrawErrInfo.E0.getCode());
		BAOFOO_PAY_UNSOLVED_STATUS.add(WithdrawErrInfo.E0000.getCode());
		BAOFOO_PAY_UNSOLVED_STATUS.add(WithdrawErrInfo.E0300.getCode());
		BAOFOO_PAY_UNSOLVED_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_3));

		BAOFOO_PAY_SUCCESS_STATUS.add(WithdrawErrInfo.S.getCode());
		BAOFOO_PAY_SUCCESS_STATUS.add(WithdrawErrInfo.E1.getCode());
		BAOFOO_PAY_SUCCESS_STATUS.add(WithdrawErrInfo.E2.getCode());
		BAOFOO_PAY_SUCCESS_STATUS.add(WithdrawErrInfo.E200.getCode());
		BAOFOO_PAY_SUCCESS_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_4));
		BAOFOO_PAY_SUCCESS_STATUS.add(String.valueOf(XjdLifeCycle.LC_CASH_2));
	}

	/**
	 * 基于支付订单进行付款： 1、默认情况下付款到当前的用户的电子账户
	 * 
	 * @param order
	 * @throws Exception
	 */
	public void withraw(RepayLogVO order) {
		CustUserVO user = userService.getCustUserById(order.getUserId());
		// 交易成功金额，单位：元
		double totalAmt = order.getSuccAmt().doubleValue();

		// 恒丰银行给聚宝钱包分配的卡BIN为6212
		// 现阶段，只能提现到恒丰银行电子账户
		String prefix = "6212";
		String cnName = user.getRealName();
		String mobile = user.getMobile();
		String idNo = user.getIdNo();
		String accountid = user.getAccountId();
		System.out.println(accountid);
		if (user != null && StringUtils.startsWith(accountid, prefix)) {
			withdraw(order, cnName, idNo, mobile, accountid, order.getSuccAmt());
		} else {
			logger.error("宝付支付-代付：代付失败：{}，{}", order.getUserId(), order.getId());
		}

	}

	/**
	 * 付款
	 * 
	 * @param order
	 * @param cnName
	 * @param idNo
	 * @param mobile
	 * @param accountid
	 * @param payAmt
	 */
	public void withdraw(RepayLogVO order, String cnName, String idNo, String mobile, String accountid,
			BigDecimal payAmt) {
		PayLogVO pw = new PayLogVO();
		pw.setOrigOrderNo(order.getId());
		pw.setId("WD" + System.nanoTime());
		pw.setIsNewRecord(true);
		Double totalAmt = order.getSuccAmt().doubleValue();
		// 根据充值成功金额进行付款(单位：元)
		pw.setTxAmt(payAmt);
		pw.setUserId(order.getUserId());
		pw.setUserName(cnName);
		pw.setApplyId(order.getApplyId());
		pw.setContractNo(order.getContractId());
		// pw.setMemberId(BaofooConfig.member_id);
		pw.setTerminalId(BaofooConfig.withdraw_terminal_id);
		pw.setTxType("withdraw");
		pw.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
		pw.setTxTime(new Date());
		pw.setToAccName(cnName);
		pw.setToAccNo(accountid);
		pw.setStatus(WithdrawErrInfo.P.getCode());
		pw.setRemark(WithdrawErrInfo.P.getMsg());
		pw.setToBankName("恒丰银行");
		pw.setToProName("浙江省");
		pw.setToCityName("杭州市");
		pw.setToAccDept("总行营业部");
		pw.setToIdno(idNo);
		pw.setToMobile(mobile);
		String txFee = BaofooFeeUtils.computeWithdrawFee(payAmt.toString());
		pw.setTxFee(MoneyUtils.toDecimal(txFee));
		logger.info("宝付-代付，准备付款到电子账户：{}，{}元，{}", cnName, payAmt, accountid);
		Double succPayedAmt = payLogService.findPayedAmt(order.getId());
		if (totalAmt > succPayedAmt) {
			logger.info("宝付-代付，该笔充值订单总金额：{}元，已经付款：{}元，尚未付款：{}元", totalAmt, succPayedAmt, totalAmt - succPayedAmt);
			// 向宝付发送代付指令
			sendWithdrawCommand(pw);
		} else {
			logger.warn("宝付-代付，该笔充值订单已经代付，请勿重复付款：{}，{}元，{}", cnName, payAmt, accountid);
		}
	}

	/**
	 * 重新付款
	 * 
	 * @param pw
	 */
	@Transactional
	public AdminWebResult reWithdraw(String payNo) {
		PayLogVO pw = payLogService.get(payNo);
		if (!BAOFOO_PAY_UNSOLVED_STATUS.contains(pw.getStatus())
				&& !BAOFOO_PAY_SUCCESS_STATUS.contains(pw.getStatus())) {
			String userId = pw.getUserId();
			String applyId = pw.getApplyId();
			List<String> unsolvedAndSuccessStatus = new ArrayList<String>();
			unsolvedAndSuccessStatus.addAll(BAOFOO_PAY_UNSOLVED_STATUS);
			unsolvedAndSuccessStatus.addAll(BAOFOO_PAY_SUCCESS_STATUS);
			if (payLogService.countBaofooPayUnsolvedAndSuccess(applyId, unsolvedAndSuccessStatus) > 0) {
				return new AdminWebResult("99", "订单已放款成功或正在处理中");
			}
			CustUserVO custUserVO = userService.getCustUserById(userId);

			PayLogVO newPw = (PayLogVO) BeanMapper.map(pw, PayLogVO.class);
			newPw.setToAccName(custUserVO.getRealName());
			newPw.setToAccNo(custUserVO.getCardNo());
			newPw.setToIdno(custUserVO.getIdNo());
			newPw.setToMobile(StringUtils.isNotBlank(custUserVO.getBankMobile()) ? custUserVO.getBankMobile()
					: custUserVO.getMobile());
			newPw.setToBankName(BankCodeEnum.getName(custUserVO.getBankCode()));
			newPw.setIsNewRecord(true);
			newPw.setTxType("withdraw");
			newPw.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
			newPw.setTxTime(new Date());
			newPw.setStatus(WithdrawErrInfo.P.getCode());
			newPw.setRemark(WithdrawErrInfo.P.getMsg());
			newPw.setId("LS" + DateUtils.getHHmmss() + (int) (new Random().nextInt(900) + 100));
			// String mobile = BaofooConfig.alarm_mobiles;
			// String message = "代付订单异常提醒：" + newPw.getToAccName() + ",正在重新付款" +
			// newPw.getTxAmt() + "元,请核对此事!";
			// sendAlarmSms(message);
			// 向宝付发送代付指令
			sendWithdrawCommand(newPw);
			return new AdminWebResult("1", "重新放款成功");
		}
		return new AdminWebResult("99", "订单状态错误");
		// payLogService.delete(pw);
	}

	/**
	 * 向宝付发送代付指令
	 * 
	 * @param pw
	 * @throws Exception
	 */
	@Transactional
	public boolean sendWithdrawCommand(PayLogVO pw) {
		boolean flag = Boolean.FALSE;
		// 数据类型 xml/json
		String dataType = TransConstant.data_type_xml;

		TransContent<TransReqBF0040001> transContent = new TransContent<TransReqBF0040001>(dataType);
		List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();
		TransReqBF0040001 transReqData = new TransReqBF0040001();
		transReqData.setTrans_no(pw.getId());
		transReqData.setTrans_money(pw.getTxAmt().toString());
		transReqData.setTo_acc_name(pw.getToAccName());
		transReqData.setTo_acc_no(pw.getToAccNo());
		transReqData.setTo_bank_name(pw.getToBankName());
		transReqData.setTrans_card_id(pw.getToIdno());
		transReqData.setTrans_mobile(pw.getToMobile());
		trans_reqDatas.add(transReqData);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		logger.info("宝付-代付-请求报文：" + bean2XmlString);

		String keyStorePath = this.getClass().getResource(BaofooConfig.baofoo_keystore_path).getPath();
		String keyStorePassword = BaofooConfig.baofoo_keystore_password;
		String pubKeyPath = this.getClass().getResource(BaofooConfig.baofoo_pubkey_path).getPath();
		String origData = bean2XmlString;

		try {
			/**
			 * 加密规则：项目编码UTF-8 第一步：BASE64 加密 第二步：商户私钥加密
			 */
			origData = new String(SecurityUtil.Base64Encode(origData));
			String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData, keyStorePath, keyStorePassword);

			// logger.info("【宝付-代付-私钥加密】{}" , encryptData);
			// 发送请求
			String requestUrl = BaofooConfig.baofoo_withdraw_url;
			// 商户号
			String memberId = BaofooConfig.baofoo_member_id;
			// 终端号
			String terminalId = BaofooConfig.baofoo_withdraw_terminal_id;

			RequestParams params = new RequestParams();
			params.setMemberId(Integer.parseInt(memberId));
			params.setTerminalId(Integer.parseInt(terminalId));
			params.setDataType(dataType);
			// 加密后数据
			params.setDataContent(encryptData);
			params.setVersion("4.0.0");
			params.setRequestUrl(requestUrl);
			SimpleHttpResponse response = null;
			;
			response = BaofooClient.doRequest(params);
			// System.out.println("宝付请求返回结果：" + response.getEntityString());
			TransContent<TransRespBF0040001> resp = new TransContent<TransRespBF0040001>(dataType);
			String result = response.getEntityString();
			// logger.info("宝付-代付-应答结果(原始)：{}" , result);
			/**
			 * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
			 * 
			 * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
			 * 
			 * 再次通过BASE64解密：new String(new Base64().decode(reslut))
			 * 
			 * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
			 */
			if (StringUtils.isNotBlank(result)) {
				// 明文返回处理可能是报文头参数不正确、或其他的异常导致；
				if (result.contains("trans_content")) {
					// 明文返回
					// 我报文错误处理
					resp = (TransContent<TransRespBF0040001>) resp.str2Obj(result, TransRespBF0040001.class);
					// 业务逻辑判断
				} else {
					// 密文返回
					// 第一步：公钥解密
					result = RsaCodingUtil.decryptByPubCerFile(result, pubKeyPath);
					// 第二步BASE64解密
					result = SecurityUtil.Base64Decode(result);
					resp = (TransContent<TransRespBF0040001>) resp.str2Obj(result, TransRespBF0040001.class);
				}
			} else {
				throw new BizException("宝付-代付：应答报文解密失败，请检查代付配置参数或者网络连接");
			}
			logger.info("宝付-代付-应答结果(解密)：{}", result);
			TransHead head = resp.getTrans_head();
			if (head != null) {
				if ("0000".equals(head.getReturn_code())) {
					List<TransRespBF0040001> datas = resp.getTrans_reqDatas();
					TransRespBF0040001 ts = datas.get(0);
					pw.setChlOrderNo(ts.getTrans_orderid());
					flag = Boolean.TRUE;
				}
				pw.setStatus(head.getReturn_code());
				pw.setRemark(head.getReturn_msg());
			} else {
				throw new BizException("宝付-代付：应答报文缺少报文头");
			}
		} catch (Exception e) {
			logger.error("宝付-代付-异常：{}", e.getMessage());
			pw.setStatus(WithdrawErrInfo.E_1.getCode());
			String remarks = StringUtils.substring(e.getMessage(), 0, 99);
			pw.setRemark(remarks);
			e.printStackTrace();
		} finally {
			payLogService.save(pw);
		}
		return flag;
	}

	/**
	 * 代付交易状态查证
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public TransContent<TransRespBF0040002> queryWithdrawResult(String orderNo) throws Exception {

		TransContent<TransReqBF0040002> transContent = new TransContent<TransReqBF0040002>(TransConstant.data_type_xml);

		List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();

		TransReqBF0040002 transReqData = new TransReqBF0040002();

		transReqData.setTrans_no(orderNo);

		trans_reqDatas.add(transReqData);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		logger.info("宝付-查询代付结果-请求报文：" + bean2XmlString);

		String keyStorePath = this.getClass().getResource(BaofooConfig.baofoo_keystore_path).getPath();
		String keyStorePassword = BaofooConfig.keystore_password;
		String pubKeyPath = this.getClass().getResource(BaofooConfig.baofoo_pubkey_path).getPath();
		String origData = bean2XmlString;
		/**
		 * 加密规则：项目编码UTF-8 第一步：BASE64 加密 第二步：商户私钥加密
		 */
		origData = new String(SecurityUtil.Base64Encode(origData));// Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData, keyStorePath, keyStorePassword);

		logger.debug("----------->【宝付-查询代付结果-私钥加密】{}", encryptData);

		// 发送请求
		String requestUrl = BaofooConfig.withdraw_result_url;
		// 商户号
		String memberId = BaofooConfig.baofoo_member_id;
		// 终端号
		String terminalId = BaofooConfig.baofoo_withdraw_terminal_id;
		// 数据类型 xml/json
		String dataType = TransConstant.data_type_xml;

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt(memberId));
		params.setTerminalId(Integer.parseInt(terminalId));
		params.setDataType(dataType);
		// 加密后数据
		params.setDataContent(encryptData);
		params.setVersion("4.0.0");
		params.setRequestUrl(requestUrl);
		SimpleHttpResponse response = BaofooClient.doRequest(params);

		TransContent<TransRespBF0040002> resp = new TransContent<TransRespBF0040002>(dataType);

		String reslut = response.getEntityString();

		logger.debug("宝付-查询代付结果-应答结果：{}", reslut);

		/**
		 * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
		 * 
		 * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
		 * 
		 * 再次通过BASE64解密：new String(new Base64().decode(reslut))
		 * 
		 * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
		 */
		if (reslut.contains("trans_content")) {
			// 报文错误处理
			resp = (TransContent<TransRespBF0040002>) resp.str2Obj(reslut, TransRespBF0040002.class);
			// 业务逻辑判断
		} else {
			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pubKeyPath);
			reslut = SecurityUtil.Base64Decode(reslut);
			resp = (TransContent<TransRespBF0040002>) resp.str2Obj(reslut, TransRespBF0040002.class);
			// 业务逻辑判断
		}
		logger.info("代付交易状态查证：{}", reslut);
		return resp;
	}

	/**
	 * 对未成功的付款订单进行处理： 1、每隔10分钟定时跑批，查询未成功的付款订单 2、通过"代付交易状态查证接口(BF0040002)"进行查证
	 * 3、如果宝付进行了拆单，记录拆单结果 4、根据查证结果，更新本地付款订单状态 5、如果确认付款订单失败，短信通知相关人员
	 * 
	 * @throws Exception
	 */
	public TaskResult processUnsolvedOrders() {
		Calendar calendar = Calendar.getInstance();
		TaskResult result = new TaskResult();
		List<PayLogVO> pwList = payLogService.findBaofooPayUnsolvedOrders(BAOFOO_PAY_UNSOLVED_STATUS);
		logger.debug("宝付支付-代付-检查付款结果：{}有【{}】条订单尚未处理完成", DateUtils.formatDateTime(calendar.getTime()), pwList.size());
		TransContent<TransRespBF0040002> respList = null;
		String orderNo = null;
		String succCode = ErrInfo.SUCCESS.getCode();
		int succNum = 0;
		for (PayLogVO pw : pwList) {
			orderNo = pw.getId();
			try {
				Thread.sleep(500);
				respList = queryWithdrawResult(orderNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 宝付返回结果非空
			if (respList != null && respList.getTrans_reqDatas() != null && !respList.getTrans_reqDatas().isEmpty()) {
				// 该笔订单尚未处理完成
				if (WithdrawErrInfo.E0000.getCode().equals(respList.getTrans_head().getReturn_code())
						&& !succCode.equals(pw.getStatus())) {
					Double txFee = 0D;
					Double txAmt = 0D;
					Boolean allSolved = true;
					Boolean doing = false;// 处理中
					List<String> remarks = new ArrayList<String>();
					String remark = null;
					Date succTime = null;
					// 是否宝付拆单
					Boolean hasSubOrder = respList.getTrans_reqDatas().size() > 1;
					for (TransRespBF0040002 resp : respList.getTrans_reqDatas()) {
						logger.info("宝付支付-代付-检查付款结果：{}，{}元，{}，{}", pw.getToAccName(), resp.getTrans_money(),
								getOrderRemark(resp.getState()), pw.getId());
						if (StringUtils.isNotBlank(resp.getTrans_fee())) {
							txFee += Double.parseDouble(resp.getTrans_fee());
						}
						if (StringUtils.isNotBlank(resp.getTrans_money())) {
							txAmt += Double.parseDouble(resp.getTrans_money());
						}
						allSolved = (allSolved) && (WithdrawErrInfo.E1.getCode().equals(resp.getState()));
						// remark = resp.getTrans_money() + "元," +
						// getOrderRemark(resp.getState());
						remark = resp.getTrans_money() + "元," + resp.getTrans_remark();
						remarks.add(remark);
						succTime = DateUtils.parseDate(resp.getTrans_endtime());

						doing = WithdrawErrInfo.E0.getCode().equals(resp.getState());
						if (doing) {
							allSolved = false;
							break;
						}
					}
					pw.setUpdateTime(new Date());
					pw.setTxFee(new BigDecimal(txFee));
					remark = StringUtils.substring(StringUtils.join(remarks, "|"), 0, 99);// 避免备注字段撑爆
					if (allSolved) {
						pw.setStatus(String.valueOf(XjdLifeCycle.LC_CASH_4));
						pw.setSuccTime(succTime);
						pw.setSuccAmt(new BigDecimal(txAmt));
						pw.setRemark("付款成功");
						// String origOrderNo = pw.getOrigOrderNo();
						// String payStatus = succCode;
						// System.out.println(JsonMapper.toJsonString(pw));
						// repayLogService.updatePayStatus(origOrderNo,
						// payStatus);
						succNum++;

						// 付款成功生成合同
						try {
							PactRecord pactRecord = carefreeCounterfoilService.generate(pw.getUserId(), pw.getApplyId(),
									pw.getId(), new Date());
							if (pactRecord != null) {
								pw.setContractUrl(
										pactRecord.getLoanRecordNo() + "," + pactRecord.getShoppingRecordNo());
							}
						} catch (Exception e1) {
							logger.error("生成合同异常" + e1.toString());
						}
					} else {
						// String mobile = BaofooConfig.alarm_mobiles;
						// String url = getReWithdrawUrl(pw);
						// String message = "代付异常提醒：" + pw.getToAccName() + ","
						// + txAmt + "元," + remark + "。" + url;
						// pw.setStatus(ErrInfo.ERROR.getCode());
						if (doing) {
							pw.setStatus(String.valueOf(XjdLifeCycle.LC_CASH_3));
							pw.setRemark("提现处理中");
						} else {
							pw.setStatus(String.valueOf(XjdLifeCycle.LC_CASH_5));
							pw.setRemark(remark);
						}
						// sendAlarmSms(message);
						// reWithdraw(pw);
					}
					int updateRows = payLogService.updatePayResult(pw);
					logger.info("宝付支付-代付-检查付款结果：{}更新本地订单状态", 1 == updateRows ? "已" : "未");
					// 该笔付款被宝付拆单
					if (hasSubOrder) {
						logger.info("宝付支付-代付-检查付款结果：该笔订单被拆单，分为{}次付款", respList.getTrans_reqDatas().size());
					}
				}

			} else {
				// String url = getReWithdrawUrl(pw);
				// String message = "网络异常，未查询到付款信息：" + pw.getToAccName() + "," +
				// pw.getTxAmt() + "元," + pw.getRemark()
				// + "。" + url;
				// sendAlarmSms(message);
				// 宝付返回结果为空
				logger.info("宝付支付-代付-检查付款结果：根据订单号，没有查询到付款结果信息");
			}
			// 结束pwList遍历
		}
		result.setSuccNum(succNum);
		result.setFailNum(pwList.size() - succNum);
		return result;
	}

	/**
	 * 重新付款接口
	 * 
	 * @param pw
	 * @return
	 */
	private String getReWithdrawUrl(PayLogVO pw) {
		String url = "http://loans-pay.rongdu.com/api/withdraw/re-withdraw?userId=" + pw.getUserId() + "&orderNo="
				+ pw.getId() + "&origOrderNo=" + pw.getOrigOrderNo();
		return url;
	}

	/**
	 * 发送提醒短信
	 * 
	 * @param message
	 * @throws UnsupportedEncodingException
	 * @throws RestClientException
	 */
	private void sendAlarmSms(String message) {
		String mobile = BaofooConfig.alarm_mobiles;
		alarmService.sendAlarmSms(mobile, message);
		// String url =
		// "http://apo.rongdu.com/sms/sendSms.form?mobile={mobile}&content={message}";
		// RestTemplate client = new RestTemplate();
		// try {
		// logger.info("宝付支付-代付-发送短信：{}，【{}】", mobile,message);
		// String result = client.getForObject(url, String.class, mobile,new
		// String(message.getBytes("utf-8"), "iso-8859-1"));
		// } catch (RestClientException e) {
		// e.printStackTrace();

		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 什么情况下可以发起重新付款？ 1、该笔付款不成功（本地）：status!=S 2、该笔付款申请失败（宝付）：return_code!=0000
	 * 3、该笔付款申请成功，但是实际银行或者宝付处理失败（宝付）：return_code==0000，state=-1 注意：
	 * 1、处理中、付款成功的订单不能重复付款
	 * 
	 * @return
	 */
	public boolean isReWithdraw(String orderNo, PayLogVO pw) {
		TransContent<TransRespBF0040002> content = null;
		try {
			content = queryWithdrawResult(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String succCode = ErrInfo.SUCCESS.getCode();
		if (content == null || succCode.equals(pw.getStatus())) {
			return false;
		} else {
			String returnCode = content.getTrans_head().getReturn_code();
			if (WithdrawErrInfo.E0000.getCode().equals(returnCode)) {
				List<TransRespBF0040002> list = content.getTrans_reqDatas();
				boolean temp = true;
				// 必须全部处理失败（state=-1）才能重新发起付款
				for (TransRespBF0040002 resp : list) {
					if ("-1".equals(resp.getState())) {
						temp = temp && true;
					} else {
						temp = temp && false;
					}
				}
				return temp;
			} else {
				return false;
			}
		}
	}

	/**
	 * 获取订单备注
	 * 
	 * @param state
	 * @return
	 */
	public String getOrderRemark(String state) {
		for (WithdrawErrInfo err : WithdrawErrInfo.values()) {
			if (err.getCode().equals(state)) {
				return err.getMsg();
			}
		}
		return "未知";
	}

	@Override
	public boolean payment(PayLogVO pw) {
		pw.setId("LS" + DateUtils.getHHmmss() + (int) (new Random().nextInt(900) + 100));
		pw.setIsNewRecord(true);
		pw.setTxType("withdraw");
		pw.setTxDate(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
		pw.setTxTime(new Date());
		pw.setStatus(WithdrawErrInfo.P.getCode());
		pw.setRemark(WithdrawErrInfo.P.getMsg());
		int record = payLogService.findPaymentRecord(pw.getApplyId());
		if (record > 0) {
			logger.warn("宝付-代付，该笔代付申请订单已经提交成功，请勿重复付款：{}，{}元，{}", pw.getToAccName(), pw.getTxAmt(), pw.getToAccNo());
			return Boolean.FALSE;
		}
		logger.info("宝付-代付，准备付款到账户：{}，{}元，{}", pw.getToAccName(), pw.getTxAmt(), pw.getToAccNo());
		// 向宝付发送代付指令
		return sendWithdrawCommand(pw);
	}

	/**
	 * 宝付余额查询
	 * 
	 * @param account_type
	 *            账户类型
	 * @throws Exception
	 */
	@Override
	public String queryBaofooBalance(String account_type) {
		logger.debug("========宝付余额查询===========");
		Map<String, String> PostParams = new HashMap<String, String>();

		// PostParams.put("member_id",
		// BaofooConfig.baofoo_queryBalance_member_id);// 商户号
		// PostParams.put("terminal_id",
		// BaofooConfig.baofoo_queryBalance_terminal_id);// 终端号
		PostParams.put("member_id", BaofooConfig.baofoo_member_id);// 商户号
		PostParams.put("terminal_id", BaofooConfig.baofoo_withdraw_terminal_id);
		PostParams.put("return_type", TransConstant.data_type_json);// 返回报文数据类型xml
																	// 或json
		PostParams.put("trans_code", "BF0001");// 交易码
		PostParams.put("version", "4.0");// 版本号
		PostParams.put("account_type", account_type);// 帐户类型--0:全部、1:基本账户、2:未结算账户、3:冻结账户、4:保证金账户、5:资金托管账户；

		String MAK = "&";// 分隔符
		// String KeyString = BaofooConfig.baofoo_queryBalance_key;
		String KeyString = BaofooConfig.baofoo_keystore_password;

		String Md5AddString = "member_id=" + PostParams.get("member_id") + MAK + "terminal_id="
				+ PostParams.get("terminal_id") + MAK + "return_type=" + PostParams.get("return_type") + MAK
				+ "trans_code=" + PostParams.get("trans_code") + MAK + "version=" + PostParams.get("version") + MAK
				+ "account_type=" + PostParams.get("account_type") + MAK + "key=" + KeyString;
		logger.info("Md5拼接字串:" + Md5AddString);// 商户在正式环境不要输出此项以免泄漏密钥，只在测试时输出以检查验签失败问题

		String Md5Sing = SecurityUtil.MD5(Md5AddString).toUpperCase();// 必须为大写
		PostParams.put("sign", Md5Sing);
		String Re_Url = BaofooConfig.baofoo_queryBalance_url;// 请求地址
		String result = HttpUtil.RequestForm(Re_Url, PostParams);
		logger.info("宝付余额查询返回：" + result);
		return result;

	}

}

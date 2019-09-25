package com.rongdu.loans.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.json.JSON;
import com.google.common.collect.Maps;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.security.Digests;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.service.AlarmService;
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
import com.rongdu.loans.pay.op.AuthPayQueryOP;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.PreAuthPayOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.PartnerApiService;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.BaofooFeeUtils;
import com.rongdu.loans.pay.vo.AuthPayQueryResultVO;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;

/**
 * Created by lee on 2018/4/12.
 */
@Service(value = "baofooAgreementPayService")
public class BaofooAgreementPayServiceImpl extends PartnerApiService implements BaofooAgreementPayService {

	@Autowired
	private AlarmService alarmService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private CustUserService userService;
	@Autowired
	private BindCardService bindCardService;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 协议支付
	 * 
	 * @param param
	 */
	@Override
	@Transactional
	public ConfirmAuthPayVO agreementPay(PreAuthPayOP param) throws Exception {
		logger.debug("========08-协议支付直接支付交易===========");
		String txnType = "08";
		ConfirmAuthPayVO vo = new ConfirmAuthPayVO();
		String orderNo = "AMP" + txnType + System.nanoTime();
		CustUserVO user = userService.getCustUserById(param.getUserId());
		String protocolNo = user.getProtocolNo();
		String aesKey = BaofooConfig.md5_key;
		protocolNo = SecurityUtil.AesEncrypt(SecurityUtil.Base64Encode(protocolNo), aesKey);
		String cardInfo = "";

		Map<String, String> resp = Maps.newHashMap();
		Map<String, String> params = createParams(txnType);
		String txnAmtFen = MoneyUtils.yuan2fen(param.getTxAmt());
		params.put("txn_amt", txnAmtFen);
		params.put("protocol_no", protocolNo);
		// params.put("user_id", param.getUserId());
		params.put("trans_id", orderNo);
		params.put("card_info", cardInfo);// 卡信息

		Map<String, String> riskItem = Maps.newHashMap();
		/** --------风控基础参数------------- **/
		riskItem.put("goodsCategory", "02");// 商品类目 详见附录《商品类目》
		riskItem.put("chPayIp", StringUtils.isNotBlank(param.getIpAddr()) ? param.getIpAddr() : "127.0.0.1");// 持卡人支付IP
		params.put("risk_item", JSON.json(riskItem).toString());

		resp = sendRequest(params);
		if (resp != null) {
			String code = resp.get("biz_resp_code").toString();
			String msg = resp.get("biz_resp_msg").toString();
			vo.setOrderNo(resp.get("trans_id"));
			if (resp.get("resp_code").toString().equals("S") && "0000".equals(code)) {
				String succAmtFen = resp.get("succ_amt");
				String succAmtYuan = MoneyUtils.fen2yuan(succAmtFen);
				String succTime = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
				vo.setSuccess(true);
				vo.setSuccAmt(succAmtYuan);
				vo.setSuccTime(succTime);
			} else {
				vo.setSuccess(false);
			}
			vo.setCode(code);
			vo.setMsg(msg);
			logger.info("协议支付-直接支付结果：{}，{}，{}", user.getMobile(), code, msg);
		} else {
			logger.error("宝付支付-协议支付-返回为空");
			String mobiles = BaofooConfig.alarm_mobiles;
			String message = String.format("支付异常提醒：%s正在还款%s元，遇到异常", user.getRealName(), param.getTxAmt());
			alarmService.sendAlarmSms(mobiles, message);
		}
		saveRepayLog(user, param, orderNo, resp);
		return vo;
	}

	@Override
	public AuthPayQueryResultVO queryAgreementPayResult(AuthPayQueryOP op) {
		logger.debug("========07-协议支付交易状态查询交易===========");
		String txnType = "07";
		Map<String, String> params = createParams(txnType);

		// 原始商户订单号
		params.put("orig_trans_id", op.getOrigTransId());
		params.put("orig_trade_date", op.getOrigTradeDate());
		Map<String, String> resp = Maps.newHashMap();
		try {
			resp = sendRequest(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AuthPayQueryResultVO result = null;
		if (resp != null) {
			result = new AuthPayQueryResultVO();
			result.setCode(resp.get("biz_resp_code"));
			result.setMsg(resp.get("biz_resp_msg"));
			result.setOrigTransId(resp.get("trans_id"));
			result.setTransNo(resp.get("trans_id"));
			result.setSuccAmt(resp.get("succ_amt"));
			result.setOrderStat(resp.get("resp_code"));
			return result;
		}
		return null;
	}

	/**
	 * 预绑卡
	 * 
	 * @param param
	 */
	@Override
	public BindCardResultVO agreementPreBind(DirectBindCardOP param) throws Exception {
		logger.debug("========01-协议支付预绑卡===========");
		String txnType = "01";
		String url = BaofooConfig.agreement_pay_url;
		String pfxpath = this.getClass().getResource(BaofooConfig.agreement_keystore_path).getPath();// 商户私钥
		String cerpath = this.getClass().getResource(BaofooConfig.agreement_pubkey_path).getPath();// 宝付公钥

		String aesKey = BaofooConfig.md5_key;
		String keyPwd = BaofooConfig.agreement_keystore_password;

		String dgtlEnvlp = "01|" + aesKey;// 使用接收方的公钥加密后的对称密钥，并做Base64转码，明文01|对称密钥，01代表AES[密码商户自定义]
		dgtlEnvlp = RsaCodingUtil.encryptByPubCerFile(SecurityUtil.Base64Encode(dgtlEnvlp), cerpath);// 公钥加密

		Map<String, String> dataArry = createParams(txnType);
		String cardinfo = param.getCardNo() + "|" + param.getRealName() + "|" + param.getIdNo() + "|"
				+ param.getMobile();
		cardinfo = SecurityUtil.AesEncrypt(SecurityUtil.Base64Encode(cardinfo), aesKey);
		dataArry.put("acc_info", cardinfo);
		// dataArry.put("user_id", param.getUserId());
		dataArry.put("card_type", "101");
		dataArry.put("id_card_type", "01");
		dataArry.put("dgtl_envlp", dgtlEnvlp);

		String signVStr = FormatUtil.coverMap2String(dataArry);
		String signature = SecurityUtil.sha1X16(signVStr, "UTF-8");// 签名

		String sign = SignatureUtils.encryptByRSA(signature, pfxpath, keyPwd);
		dataArry.put("signature", sign);// 签名域

		String PostString = HttpUtil.RequestForm(url, dataArry);
		Log.Write("预绑卡请求返回:" + PostString);

		Map<String, String> returnData = FormatUtil.getParm(PostString);

		if (!returnData.containsKey("signature")) {
			throw new Exception("缺少验签参数！");
		}

		String RSign = returnData.get("signature");
		returnData.remove("signature");// 需要删除签名字段
		String RSignVStr = FormatUtil.coverMap2String(returnData);
		String RSignature = SecurityUtil.sha1X16(RSignVStr, "UTF-8");// 签名

		if (SignatureUtils.verifySignature(cerpath, RSignature, RSign)) {
			Log.Write("Yes");// 验签成功
		}

		if (!returnData.containsKey("resp_code")) {
			throw new Exception("缺少resp_code参数！");
		}

		BindCardResultVO vo = new BindCardResultVO();
		String code = returnData.get("biz_resp_code").toString();
		String msg = returnData.get("biz_resp_msg").toString();
		logger.info("协议支付-预绑卡：{},{},{},{}", param.getRealName(), param.getMobile(), code, msg);
		if (returnData.get("resp_code").toString().equals("S")) {
			if (!returnData.containsKey("dgtl_envlp")) {
				throw new Exception("缺少dgtl_envlp参数！");
			}
			String RDgtlEnvlp = SecurityUtil.Base64Decode(RsaCodingUtil.decryptByPriPfxFile(
					returnData.get("dgtl_envlp"), pfxpath, keyPwd));
			String RAesKey = FormatUtil.getAesKey(RDgtlEnvlp);// 获取返回的AESkey
			// 预签约唯一码
			String uniqueCode = SecurityUtil.Base64Decode(SecurityUtil.AesDecrypt(returnData.get("unique_code"),
					RAesKey));
			Log.Write("唯一码:" + uniqueCode);
			String transId = returnData.get("msg_id").toString();
			if (("0000").equals(code)) {
				vo.setSuccess(true);
				vo.setOrderNo(transId);
				vo.setBindId(uniqueCode);
				// 预绑卡后，保存绑卡信息
				saveBindInfo(param, transId, uniqueCode, code, msg);
			} else {
				vo.setSuccess(false);
				logger.error("协议支付-预绑卡异常：{}，{}，{}", param.getMobile(), code, msg);
			}
		}
		vo.setCode(code);
		vo.setMsg(msg);
		return vo;
	}

	/**
	 * 确认绑卡
	 */
	@Override
	public BindCardResultVO agreementConfirmBind(ConfirmBindCardOP param) throws Exception {
		logger.debug("========02-协议支付确认绑卡===========");
		String txnType = "02";
		String url = BaofooConfig.agreement_pay_url;
		String pfxpath = this.getClass().getResource(BaofooConfig.agreement_keystore_path).getPath();// 商户私钥
		String cerpath = this.getClass().getResource(BaofooConfig.agreement_pubkey_path).getPath();// 宝付公钥

		String aesKey = BaofooConfig.md5_key;
		String keyPwd = BaofooConfig.agreement_keystore_password;
		String dgtlEnvlp = "01|" + aesKey;// 使用接收方的公钥加密后的对称密钥，并做Base64转码，明文01|对称密钥，01代表AES[密码商户自定义]

		dgtlEnvlp = RsaCodingUtil.encryptByPubCerFile(SecurityUtil.Base64Encode(dgtlEnvlp), cerpath);// 公钥加密

		String uniqueCode = param.getBindId() + "|" + param.getMsgVerCode();// 预签约唯一码(预绑卡返回的值)[格式：预签约唯一码|短信验证码]

		Log.Write("预签约唯一码：" + uniqueCode);
		uniqueCode = SecurityUtil.AesEncrypt(SecurityUtil.Base64Encode(uniqueCode), aesKey);// 先BASE64后进行AES加密

		Map<String, String> dataArry = createParams(txnType);
		dataArry.put("dgtl_envlp", dgtlEnvlp);
		dataArry.put("unique_code", uniqueCode);// 预签约唯一码

		String signVStr = FormatUtil.coverMap2String(dataArry);
		String signature = SecurityUtil.sha1X16(signVStr, "UTF-8");// 签名
		String sign = SignatureUtils.encryptByRSA(signature, pfxpath, keyPwd);
		dataArry.put("signature", sign);// 签名域

		String postString = HttpUtil.RequestForm(url, dataArry);
		Log.Write("确认绑卡请求返回:" + postString);

		Map<String, String> returnData = FormatUtil.getParm(postString);

		if (!returnData.containsKey("signature")) {
			throw new Exception("缺少验签参数！");
		}

		String RSign = returnData.get("signature");
		returnData.remove("signature");// 需要删除签名字段
		String RSignVStr = FormatUtil.coverMap2String(returnData);
		String RSignature = SecurityUtil.sha1X16(RSignVStr, "UTF-8");// 签名

		if (SignatureUtils.verifySignature(cerpath, RSignature, RSign)) {
			Log.Write("Yes");// 验签成功
		}
		if (!returnData.containsKey("resp_code")) {
			throw new Exception("缺少resp_code参数！");
		}

		BindCardResultVO vo = new BindCardResultVO();
		String code = returnData.get("biz_resp_code").toString();
		String msg = returnData.get("biz_resp_msg").toString();
		if (returnData.get("resp_code").toString().equals("S")) {
			if (!returnData.containsKey("dgtl_envlp")) {
				throw new Exception("缺少dgtl_envlp参数！");
			}
			String RDgtlEnvlp = SecurityUtil.Base64Decode(RsaCodingUtil.decryptByPriPfxFile(
					returnData.get("dgtl_envlp"), pfxpath, keyPwd));
			String RAesKey = FormatUtil.getAesKey(RDgtlEnvlp);// 获取返回的AESkey

			String protocol_no = SecurityUtil.Base64Decode(SecurityUtil.AesDecrypt(returnData.get("protocol_no"),
					RAesKey));
			Log.Write("签约协议号:" + protocol_no);
			String bankCode = returnData.get("bank_code").toString();
			Log.Write("银行编码:" + bankCode);
			String bankName = returnData.get("bank_name").toString();
			Log.Write("银行名称:" + bankName);
			if (("0000").equals(code)) {
				vo.setSuccess(true);
				vo.setBindId(protocol_no);
				vo.setBankCode(bankCode);
				vo.setBankName(bankName);
			}
			logger.debug("绑卡结束，返回绑卡信息：{}，{}，{}", param.getUserId(), code, msg);
		} else if (returnData.get("resp_code").toString().equals("I")) {
			Log.Write("处理中！");
		} else if (returnData.get("resp_code").toString().equals("F")) {
			Log.Write("失败！");
		} else {
			throw new Exception("反回异常！");// 异常不得做为订单状态。
		}
		vo.setCode(code);
		vo.setMsg(msg);
		return vo;
	}

	/**
	 * 解绑卡
	 * 
	 * @param userId
	 * @param bindId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean unBind(String userId, String bindId) throws Exception {
		logger.debug("========04-解除绑定关系交易===========");
		String txnType = "04";
		String url = BaofooConfig.agreement_pay_url;
		String pfxpath = this.getClass().getResource(BaofooConfig.agreement_keystore_path).getPath();// 商户私钥
		String cerpath = this.getClass().getResource(BaofooConfig.agreement_pubkey_path).getPath();// 宝付公钥

		String aesKey = BaofooConfig.md5_key;
		String keyPwd = BaofooConfig.agreement_keystore_password;

		String dgtlEnvlp = "01|" + aesKey;// 使用接收方的公钥加密后的对称密钥，并做Base64转码，明文01|对称密钥，01代表AES[密码商户自定义]
		dgtlEnvlp = RsaCodingUtil.encryptByPubCerFile(SecurityUtil.Base64Encode(dgtlEnvlp), cerpath);// 公钥加密
		bindId = SecurityUtil.AesEncrypt(SecurityUtil.Base64Encode(bindId), aesKey);// 先BASE64后进行AES加密

		Map<String, String> dataArry = createParams(txnType);
		// dataArry.put("user_id", userId);// 用户在商户平台唯一ID (和绑卡时要一致)
		dataArry.put("dgtl_envlp", dgtlEnvlp);
		dataArry.put("protocol_no", bindId);// 签约协议号（密文）

		String signVStr = FormatUtil.coverMap2String(dataArry);
		String signature = SecurityUtil.sha1X16(signVStr, "UTF-8");// 签名
		String sign = SignatureUtils.encryptByRSA(signature, pfxpath, keyPwd);
		dataArry.put("signature", sign);// 签名域

		String postString = HttpUtil.RequestForm(url, dataArry);
		Log.Write("请求返回:" + postString);

		Map<String, String> returnData = FormatUtil.getParm(postString);

		if (!returnData.containsKey("signature")) {
			throw new Exception("缺少验签参数！");
		}

		String RSign = returnData.get("signature");
		returnData.remove("signature");// 需要删除签名字段
		String RSignVStr = FormatUtil.coverMap2String(returnData);
		String RSignature = SecurityUtil.sha1X16(RSignVStr, "UTF-8");// 签名

		if (SignatureUtils.verifySignature(cerpath, RSignature, RSign)) {
			Log.Write("Yes");// 验签成功
		}
		if (!returnData.containsKey("resp_code")) {
			throw new Exception("缺少resp_code参数！");
		}

		String code = returnData.get("biz_resp_code").toString();
		String msg = returnData.get("biz_resp_msg").toString();

		logger.debug("解绑银行卡：{}，{}，{}", bindId, code, msg);
		if (returnData.get("resp_code").toString().equals("S")) {
			if (("0000").equals(code)) {
				return true;
			}
		}
		return false;
	}

	private Map<String, String> createParams(String txnType) {
		String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Map<String, String> params = new TreeMap<String, String>();
		params.put("send_time", sendTime);
		params.put("msg_id", "SN" + txnType + System.currentTimeMillis());
		params.put("version", "4.0.0.0");
		params.put("terminal_id", BaofooConfig.agreement_terminal_id);
		params.put("txn_type", txnType);
		params.put("member_id", BaofooConfig.agreement_member_id);
		return params;
	}

	/**
	 * 保存支付订单
	 */
	@Transactional
	void saveRepayLog(CustUserVO user, PreAuthPayOP param, String orderNo, Map<String, String> returnData) {
		String txnAmtYuan = param.getTxAmt();
		RepayLogVO order = new RepayLogVO();
		order.setId(orderNo);
		order.setNewRecord(true);
		order.setUserId(user.getId());
		order.setApplyId(param.getApplyId());
		order.setContractId(param.getContractId());
		order.setRepayPlanItemId(param.getRepayPlanItemId());
		order.setPayType(param.getPayType());
		// BindCardVO bindCardVO =
		// bindCardService.findByBindId(user.getProtocolNo());
		// if (bindCardVO != null) {
		// order.setUserName(bindCardVO.getName());
		// order.setMobile(bindCardVO.getMobile());
		// order.setIdNo(bindCardVO.getIdNo());
		// order.setBankCode(bindCardVO.getBankCode());
		// order.setBankName(bindCardVO.getBankName());
		// order.setCardNo(bindCardVO.getCardNo());
		// }
		order.setUserName(user.getRealName());
		order.setIdNo(user.getIdNo());
		order.setMobile(user.getMobile());
		order.setBankCode(user.getBankCode());
		order.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
		order.setCardNo(user.getCardNo());
		order.setBindId(user.getProtocolNo());

		order.setTerminal(param.getSource());
		order.setIp(param.getIpAddr());
		order.setTxType("AM_PAY");
		order.setChlCode("BAOFOO");
		order.setChlName("宝付支付");
		order.setGoodsName("");
		order.setGoodsNum(1);
		String txFee = BaofooFeeUtils.computeAuthpayFee(txnAmtYuan);
		order.setTxAmt(MoneyUtils.toDecimal(txnAmtYuan));
		order.setTxFee(MoneyUtils.toDecimal(txFee));
		order.setTxTime(new Date());
		order.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));

		order.setSuccTime(new java.sql.Date(System.currentTimeMillis()));
		order.setSuccAmt(MoneyUtils.toDecimal(txnAmtYuan));
		order.setStatus(returnData.get("biz_resp_code").equals("0000") ? ErrInfo.SUCCESS.getCode() : returnData
				.get("biz_resp_code"));
		order.setRemark(returnData.get("biz_resp_msg"));
		order.setChlOrderNo(returnData.get("order_id"));
		logger.info("协议支付-正在保存支付订单：{}，{}元，{}", user.getRealName(), txnAmtYuan, orderNo);
		repayLogService.save(order);
	}

	private Map<String, String> sendRequest(Map<String, String> params) throws Exception {
		String url = BaofooConfig.agreement_pay_url;
		String pfxpath = this.getClass().getResource(BaofooConfig.agreement_keystore_path).getPath();// 商户私钥
		String cerpath = this.getClass().getResource(BaofooConfig.agreement_pubkey_path).getPath();// 宝付公钥
		String aesKey = BaofooConfig.md5_key;
		String keyPwd = BaofooConfig.agreement_keystore_password;
		String dgtlEnvlp = "01|" + aesKey;
		dgtlEnvlp = RsaCodingUtil.encryptByPubCerFile(SecurityUtil.Base64Encode(dgtlEnvlp), cerpath);// 公钥加密
		params.put("dgtl_envlp", dgtlEnvlp);
		String signVStr = FormatUtil.coverMap2String(params);
		String signature = SecurityUtil.sha1X16(signVStr, "UTF-8");// 签名
		String sign = SignatureUtils.encryptByRSA(signature, pfxpath, keyPwd);
		params.put("signature", sign);

		String postString = HttpUtil.RequestForm(url, params);
		Map<String, String> returnData = FormatUtil.getParm(postString);

		if (!returnData.containsKey("signature")) {
			throw new Exception("缺少验签参数！");
		}
		String RSign = returnData.get("signature");
		returnData.remove("signature");
		String RSignVStr = FormatUtil.coverMap2String(returnData);
		String RSignature = SecurityUtil.sha1X16(RSignVStr, "UTF-8");
		if (!SignatureUtils.verifySignature(cerpath, RSignature, RSign)) {
			throw new Exception("验签失败！");
		}
		if (!returnData.containsKey("resp_code")) {
			throw new Exception("缺少resp_code参数！");
		}
		return returnData;
	}

	private String generateBindCardId(String idCard, String accNo, String mobile) {
		String input = idCard + accNo + mobile;
		return Digests.md5(input);
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
	BindCardVO saveBindInfo(DirectBindCardOP param, String transId, String bindId, String code, String msg) {
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
			logger.info("协议支付-预绑卡-更新绑卡信息：{},{},{},{},{}", msg, param.getUserId(), bindInfo.getBankName(),
					param.getCardNo(), param.getMobile());
			bindCardService.update(bindInfo);
		}
		return bindInfo;
	}
}

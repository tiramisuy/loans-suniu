package com.rongdu.loans.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.rongdu.loans.enums.PayChannelEnum;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.pay.op.AuthPayOP;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.mq.MessageProductor;
import com.rongdu.loans.pay.op.XfAgreementAdminPayOP;
import com.rongdu.loans.pay.op.XfAgreementPayOP;
import com.rongdu.loans.pay.service.PartnerApiService;
import com.rongdu.loans.pay.service.XianFengAgreementPayService;
import com.rongdu.loans.pay.utils.BankLimitUtils;
import com.rongdu.loans.pay.utils.XianFengConfig;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;
import com.rongdu.loans.pay.xianfeng.vo.XfAgreementPayDataVO;
import com.rongdu.loans.pay.xianfeng.vo.XfAgreementPayQueryVO;
import com.rongdu.loans.pay.xianfeng.vo.XfAgreementPayVO;
import com.ucf.sdk.UcfForOnline;
import com.ucf.sdk.util.AESCoder;
import com.ucf.sdk.util.UnRepeatCodeGenerator;

/**
 * @Description: 先锋支付
 * @author: liuzhuang
 * @date 2018年6月13日
 * @version V1.0
 */
@Service(value = "xianFengAgreementPayService")
public class XianFengAgreementPayServiceImpl extends PartnerApiService implements XianFengAgreementPayService {
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private CustUserService userService;
	@Autowired
	private MessageProductor messageProductor;
	@Autowired
	private LoanApplyService loanApplyService;
	/**
	 * 协议支付
	 */
	@SuppressWarnings("unchecked")
	@Override
	public XfAgreementPayResultVO agreementPay(XfAgreementPayOP op) {
		String partnerName = Global.XIANFENG_CHANNEL_NAME;
		String bizName = "协议支付";
		String merchantId = XianFengConfig.merchantId;// 商户号
		String key = XianFengConfig.merRSAKey;// 密钥
		String url = XianFengConfig.gateway;// 接口地址

		CustUserVO user = userService.getCustUserById(op.getUserId());

		XfAgreementPayResultVO result = new XfAgreementPayResultVO();
		XfAgreementPayVO vo = new XfAgreementPayVO();
		XfAgreementPayDataVO data = new XfAgreementPayDataVO();
		try {
			data.setContractNo(op.getContractNo());
			data.setAmount(MoneyUtils.yuan2fen(op.getAmount()));
			data.setCertificateNo(user.getIdNo());
			data.setAccountNo(user.getCardNo());
			data.setAccountName(user.getRealName());
			if (StringUtils.isNotBlank(user.getBankMobile())) {
				data.setMobileNo(user.getBankMobile());
			} else {
				data.setMobileNo(user.getMobile());
			}
			data.setProductName(PayTypeEnum.get(op.getPayType()).getName());
			data.setExpireTime(DateUtils.formatDate(DateUtils.addDay(new Date(), 2), "yyyy-MM-dd HH:mm:ss"));

			vo.setReqSn(UnRepeatCodeGenerator.createUnRepeatCode(merchantId, vo.getService(), new SimpleDateFormat(
					"yyyyMMddhhmmssSSS").format(new Date())));
			vo.setMerchantId(merchantId);
			vo.setData(AESCoder.encrypt(JsonMapper.toJsonString(data), key));
			String sign = UcfForOnline.createSign(key, "sign", BeanMapper.describe(vo), vo.getSecId());
			vo.setSign(sign);

			logger.debug("{}-{}-请求地址：{}", partnerName, bizName, url);
			logger.debug("{}-{}-请求data：{}", partnerName, bizName, JsonMapper.toJsonString(data));
			logger.debug("{}-{}-请求报文：{}", partnerName, bizName, JsonMapper.toJsonString(vo));
			String responseString = httpUtils.postForJson(url, BeanMapper.map(vo, Map.class));
			responseString = AESCoder.decrypt(responseString, key);
			logger.debug("{}-{}-应答结果：{}", partnerName, bizName, responseString);
			// 将应答的Json映射成Java对象
			result = (XfAgreementPayResultVO) JsonMapper.fromJsonString(responseString, XfAgreementPayResultVO.class);
		} catch (Exception e) {
			logger.error("先锋协议支付异常", e);
		}
		if (result == null) {
			result = new XfAgreementPayResultVO();
		}
		if ("F".equals(result.getStatus())) {
		} else if ("I".equals(result.getStatus()) && "20009".equals(result.getResCode())) {
			result.setStatus("F");
			result.setResMessage("单笔单日交易超银行限额");
		} else {
			result.setStatus("I");// 默认全部处理中，后续查询订单结果
			result.setResMessage("交易处理中,请稍后查询");
		}
		// 代扣购物金默认I,以便跑批时处理失败情况下进行宝付代扣
		// if (PayTypeEnum.SHOPPING.getId().intValue() ==
		// op.getPayType().intValue()) {
		// result.setStatus("I");
		// result.setResMessage("交易处理中,请稍后查询");
		// }
		if (PayTypeEnum.SHOPPINGMALL.getId().intValue() == op.getPayType().intValue()) {
			result.setStatus("I");
			result.setResMessage("交易处理中,请稍后查询");
		}
		result.setMerchantNo(data.getMerchantNo());
		result.setAmountYuan(op.getAmount());
		result.setSuccess(result.isSuccess());
		// ytodo 0303 加急券 version
		/*if (PayTypeEnum.URGENT.getId().intValue() == op.getPayType().intValue()){
			// 更新订单表加急券支付状态
			loanApplyService.updateUrgentPayed(op.getApplyId(), result.getStatus());
		}*/
		/** 保存流水 */
		int rz = saveRepayLog(result, op, user);
		if (rz > 0 && "I".equals(result.getStatus())) {
			messageProductor.sendToRepayIngQueue(result);
		}
		return result;
	}

	@Override
	public ConfirmAuthPayVO pay(AuthPayOP op) {
		XfAgreementPayOP xfAgreementPayOP = BeanMapper.map(op,XfAgreementPayOP.class);
		/*xfAgreementPayOP.setContractId(op.getContractId());
		xfAgreementPayOP.setApplyId(op.getApplyId());
		xfAgreementPayOP.setUserId(op.getUserId());
		xfAgreementPayOP.setAmount(op.getAmount());
		xfAgreementPayOP.setRepayPlanItemId(op.getRepayPlanItemId());
		xfAgreementPayOP.setIp(op.getIp());
		xfAgreementPayOP.setSource(op.getSource());
		xfAgreementPayOP.setPayType(op.getPayType());*/
		XfAgreementPayResultVO xfAgreementPayResultVO = this.agreementPay(xfAgreementPayOP);
		ConfirmAuthPayVO confirmAuthPayVO = new ConfirmAuthPayVO();
		confirmAuthPayVO.setSuccess(xfAgreementPayResultVO.isSuccess());
		confirmAuthPayVO.setCode(xfAgreementPayResultVO.getResCode());
		confirmAuthPayVO.setMsg(xfAgreementPayResultVO.getResMessage());

		confirmAuthPayVO.setReqNo(xfAgreementPayResultVO.getMerchantNo());
		confirmAuthPayVO.setOrderNo(xfAgreementPayResultVO.getTradeNo());
		confirmAuthPayVO.setSuccAmt(xfAgreementPayResultVO.getAmountYuan());
		confirmAuthPayVO.setSuccTime(xfAgreementPayResultVO.getTradeTime());
		confirmAuthPayVO.setStatus(xfAgreementPayResultVO.getStatus());

		op.setPayChannel(PayChannelEnum.XIANFENG.getChannelCode());
		return confirmAuthPayVO;
	}

	/**
	 * 支付结果查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public XfAgreementPayResultVO agreementPayQuery(String merchantNo) {
		String partnerName = Global.XIANFENG_CHANNEL_NAME;
		String bizName = "协议支付查询";
		String merchantId = XianFengConfig.merchantId;// 商户号
		String key = XianFengConfig.merRSAKey;// 密钥
		String url = XianFengConfig.gateway;// 接口地址

		XfAgreementPayResultVO result = new XfAgreementPayResultVO();
		XfAgreementPayQueryVO vo = new XfAgreementPayQueryVO();
		try {
			vo.setReqSn(UnRepeatCodeGenerator.createUnRepeatCode(merchantId, vo.getService(), new SimpleDateFormat(
					"yyyyMMddhhmmssSSS").format(new Date())));
			vo.setMerchantId(merchantId);
			vo.setMerchantNo(merchantNo);
			String sign = UcfForOnline.createSign(key, "sign", BeanMapper.describe(vo), vo.getSecId());
			vo.setSign(sign);

			logger.debug("{}-{}-请求地址：{}", partnerName, bizName, url);
			logger.debug("{}-{}-请求报文：{}", partnerName, bizName, JsonMapper.toJsonString(vo));
			String responseString = httpUtils.postForJson(url, BeanMapper.map(vo, Map.class));
			responseString = AESCoder.decrypt(responseString, key);
			logger.debug("{}-{}-应答结果：{},{}", partnerName, bizName, merchantNo, responseString);
			// 将应答的Json映射成Java对象
			result = (XfAgreementPayResultVO) JsonMapper.fromJsonString(responseString, XfAgreementPayResultVO.class);
		} catch (Exception e) {
			logger.error("先锋协议支付查询异常", e);
		}
		if (StringUtils.isBlank(result.getStatus())) {
			result.setStatus("I");
		}
		// 10009=交易记录不存在
		if ("10009".equals(result.getResCode())) {
			String cacheKey = "xianfeng_pay_code_count_" + merchantNo + "_" + result.getResCode();
			String s = JedisUtils.get(cacheKey);
			int count = (s == null ? 0 : Integer.parseInt(s));
			if (count < 3) {
				count++;
				JedisUtils.set(cacheKey, String.valueOf(count), 30 * 60);
			} else {
				result.setStatus("F");
				result.setResMessage("10009交易记录不存在");
			}
		}
		result.setSuccess(result.isSuccess());
		return result;
	}

	/**
	 * 保存支付流水
	 * 
	 * @param result
	 * @param op
	 * @param user
	 * @return
	 */
	private int saveRepayLog(XfAgreementPayResultVO result, XfAgreementPayOP op, CustUserVO user) {
		String orderNo = result.getMerchantNo();
		RepayLogVO order = new RepayLogVO();
		order.setId(orderNo);
		order.setNewRecord(true);
		order.setUserId(op.getUserId());
		order.setApplyId(op.getApplyId());
		order.setContractId(op.getContractId());
		order.setRepayPlanItemId(op.getRepayPlanItemId());

		order.setUserName(user.getRealName());
		order.setIdNo(user.getIdNo());
		order.setMobile(user.getMobile());
		order.setBankCode(user.getBankCode());
		order.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
		order.setCardNo(user.getCardNo());
		order.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());

		if ("99".equals(op.getSource())) {// 后台代扣
			order.setTxType("WITHHOLD");
		} else {
			order.setTxType("AUTH_PAY");
		}
		order.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		order.setTxTime(new Date());
		order.setTxAmt(new BigDecimal(result.getAmountYuan()));
		order.setTxFee(BigDecimal.ZERO);

		order.setTerminal(op.getSource());
		order.setIp(op.getIp());
		order.setChlOrderNo(result.getTradeNo());
		order.setChlCode(Global.XIANFENG_CHANNEL_CODE);
		order.setChlName(Global.XIANFENG_CHANNEL_NAME);
		order.setGoodsName("聚宝钱包还款");
		order.setGoodsNum(1);
		if (StringUtils.isNotBlank(op.getGoodsId())) {
			order.setGoodsId(op.getGoodsId());
		}

		order.setStatus(result.getStatus());
		order.setRemark(result.getResMessage());
		order.setPayType(op.getPayType());
		if (result.isSuccess()) {
			order.setSuccAmt(new BigDecimal(result.getAmountYuan()));
			order.setSuccTime(new Date());
			order.setStatus(ErrInfo.SUCCESS.getCode());
		}

		order.setCouponId(op.getCouponId());//优惠券ID
		logger.debug("协议支付-正在保存支付订单：{}，{}元，{}", user.getRealName(), result.getAmountYuan(), orderNo);
		int rz = repayLogService.save(order);
		return rz;
	}

	/*****************************************************************************************************************/
	/**
	 * 后台手动代扣
	 */
	@SuppressWarnings("unchecked")
	@Override
	public XfAgreementPayResultVO agreementAdminPay(XfAgreementAdminPayOP op) {
		String partnerName = Global.XIANFENG_CHANNEL_NAME;
		String bizName = "协议支付";
		String merchantId = XianFengConfig.merchantId;// 商户号
		String key = XianFengConfig.merRSAKey;// 密钥
		String url = XianFengConfig.gateway;// 接口地址

		XfAgreementPayResultVO result = new XfAgreementPayResultVO();
		XfAgreementPayVO vo = new XfAgreementPayVO();
		XfAgreementPayDataVO data = new XfAgreementPayDataVO();
		try {
			data.setContractNo(op.getContractNo());
			data.setAmount(MoneyUtils.yuan2fen(op.getAmount()));
			data.setCertificateNo(op.getIdNo());
			data.setAccountNo(op.getCardNo());
			data.setAccountName(op.getUserName());
			data.setMobileNo(op.getMobile());
			data.setProductName(op.getRemark());
			data.setExpireTime(DateUtils.formatDate(DateUtils.addDay(new Date(), 2), "yyyy-MM-dd HH:mm:ss"));

			vo.setReqSn(UnRepeatCodeGenerator.createUnRepeatCode(merchantId, vo.getService(), new SimpleDateFormat(
					"yyyyMMddhhmmssSSS").format(new Date())));
			vo.setMerchantId(merchantId);
			vo.setData(AESCoder.encrypt(JsonMapper.toJsonString(data), key));
			String sign = UcfForOnline.createSign(key, "sign", BeanMapper.describe(vo), vo.getSecId());
			vo.setSign(sign);

			logger.debug("{}-{}-请求地址：{}", partnerName, bizName, url);
			logger.debug("{}-{}-请求data：{}", partnerName, bizName, JsonMapper.toJsonString(data));
			logger.debug("{}-{}-请求报文：{}", partnerName, bizName, JsonMapper.toJsonString(vo));
			String responseString = httpUtils.postForJson(url, BeanMapper.map(vo, Map.class));
			responseString = AESCoder.decrypt(responseString, key);
			logger.debug("{}-{}-应答结果：{}", partnerName, bizName, responseString);
			// 将应答的Json映射成Java对象
			result = (XfAgreementPayResultVO) JsonMapper.fromJsonString(responseString, XfAgreementPayResultVO.class);
		} catch (Exception e) {
			logger.error("先锋协议支付异常", e);
		}
		if ("F".equals(result.getStatus())) {
		} else if ("I".equals(result.getStatus()) && "20009".equals(result.getResCode())) {
			result.setStatus("F");
			result.setResMessage("单笔单日交易超银行限额");
		} else {
			result.setStatus("I");// 默认全部处理中，后续查询订单结果
			result.setResMessage("交易处理中,请稍后查询");
		}
		result.setMerchantNo(data.getMerchantNo());
		result.setAmountYuan(op.getAmount());
		result.setSuccess(result.isSuccess());
		/** 保存流水 */
		int rz = saveAdminRepayLog(result, op);
		if (rz > 0 && "I".equals(result.getStatus())) {
			messageProductor.sendToRepayIngQueue(result);
		}
		return result;
	}

	/**
	 * 保存后台手动代扣流水
	 * 
	 * @param result
	 * @param op
	 * @return
	 */
	private int saveAdminRepayLog(XfAgreementPayResultVO result, XfAgreementAdminPayOP op) {
		String orderNo = result.getMerchantNo();
		RepayLogVO order = new RepayLogVO();
		order.setId(orderNo);
		order.setNewRecord(true);
		order.setUserId("");
		order.setApplyId(op.getApplyId());
		order.setContractId("");
		order.setRepayPlanItemId("");

		order.setUserName(op.getUserName());
		order.setIdNo(op.getIdNo());
		order.setMobile(op.getMobile());
		order.setBankCode("");
		order.setBankName("");
		order.setCardNo(op.getCardNo());
		order.setBindId("");

		order.setTxType(op.getTxType());// 交易类型
		order.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMDD")));
		order.setTxTime(new Date());
		order.setTxAmt(new BigDecimal(result.getAmountYuan()));
		order.setTxFee(BigDecimal.ZERO);

		order.setTerminal("");
		order.setIp("");
		order.setChlOrderNo(result.getTradeNo());
		order.setChlCode(Global.XIANFENG_CHANNEL_CODE);
		order.setChlName(Global.XIANFENG_CHANNEL_NAME);
		order.setGoodsName("聚宝钱包还款");
		order.setGoodsNum(1);
		order.setGoodsId("");
		order.setOrderInfo(op.getRemark());

		order.setStatus(result.getStatus());
		order.setRemark(result.getResMessage());
		order.setPayType(op.getPayType());
		if (result.isSuccess()) {
			order.setSuccAmt(new BigDecimal(result.getAmountYuan()));
			order.setSuccTime(new Date());
			order.setStatus(ErrInfo.SUCCESS.getCode());
		}
		logger.debug("协议支付-正在保存支付订单：{}，{}元，{}", op.getUserName(), result.getAmountYuan(), orderNo);
		int rz = repayLogService.save(order);
		return rz;
	}
}

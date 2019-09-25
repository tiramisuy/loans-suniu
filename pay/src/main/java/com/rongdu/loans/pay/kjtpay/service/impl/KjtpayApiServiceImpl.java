package com.rongdu.loans.pay.kjtpay.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kjtpay.gateway.common.domain.bankwitholding.BankWitholdingReq;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import com.kjtpay.gateway.common.domain.tradequery.SingleTradeQueryDetail;
import com.kjtpay.gateway.common.domain.tradequery.SingleTradeQueryReq;
import com.kjtpay.gateway.common.domain.transfertocard.PaymentToCardReq;
import com.kjtpay.gateway.common.domain.transfertocard.PaymentToCardRes;
import com.kjtpay.gateway.common.util.JsonMapUtil;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.HttpUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.enums.HaiErBankCodeEnum;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.pay.kjtpay.util.KjtpayUtil;
import com.rongdu.loans.pay.service.KjtpayApiService;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;

@Service("kjtpayApiService")
public class KjtpayApiServiceImpl implements KjtpayApiService {

	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustUserService custUserService;

	/**
	 * 快捷通转账到银行卡
	 */
	@Override
	public ApiResultVO transferToCard(String tradeNo, String userId, String amount) {

		CustUserVO custUser = custUserService.getCustUserById(userId);
		return transferToCard(tradeNo, custUser.getCardNo(), custUser.getRealName(), custUser.getBankCode(), amount);

	}

	/**
	 * 快捷通转账到银行卡
	 */
	@Override
	public ApiResultVO transferToCard(String tradeNo, String cardNo, String realName, String bankCode, String amount) {

		ApiResultVO resultVO = new ApiResultVO();

		PaymentToCardReq paymentToCardReq = new PaymentToCardReq();
		paymentToCardReq.setOutTradeNo(tradeNo);
		paymentToCardReq.setPayerIdentity(KjtpayUtil.partnerId);
		paymentToCardReq.setPayerIdentityType("1");
		paymentToCardReq.setAmount(amount);
		paymentToCardReq.setCurrency("CNY");

		paymentToCardReq.setBankCardNo(cardNo);
		paymentToCardReq.setBankAccountName(realName);

		paymentToCardReq.setBankCode(bankCode);
		paymentToCardReq.setBankName(HaiErBankCodeEnum.getName(bankCode));
		
		// paymentToCardReq.setBankBranchName("");
		// paymentToCardReq.setBankLineNo("");

		// paymentToCardReq.setMemo("");
		paymentToCardReq.setPayProductCode("14");
		paymentToCardReq.setBizProductCode("10221");
		paymentToCardReq.setBizNo(paymentToCardReq.getOutTradeNo());

		// paymentToCardReq.setNotifyUrl(Global.getConfig("kjtpay.transferToCardNotifyUrl"));

		RequestBase requestBase = KjtpayUtil.genRequestBase("transfer_to_card", paymentToCardReq);
		String reqString = JsonMapUtil.gsonToJson(requestBase);

		Map<String, String> params = (Map) JsonMapper.fromJsonString(reqString, Map.class);

		// 参数需要进行两次编码
		for (Entry<String, String> entry : params.entrySet()) {
			entry.setValue(URLEncoder.encode(entry.getValue()));
		}

		logger.info("{}-{}-请求地址：{}", "快捷通", "转账到银行卡", KjtpayUtil.url);
		logger.info("{}-{}-请求报文：{}", "快捷通", "转账到银行卡", reqString);
		String responseString = HttpUtils.postForJson(KjtpayUtil.url, params);
		logger.info("{}-{}-应答结果：{}", "快捷通", "转账到银行卡", responseString);
		ResponseParameter rtn = JsonMapUtil.gsonToObj(responseString, ResponseParameter.class);

		// VerifyResult verifyResult = KjtpayUtil.verify(rtn);
		//
		// if (!verifyResult.isSuccess()) {// 验签失败
		// throw new IllegalArgumentException("验签失败");
		// }

		// 处理成功
		if (rtn.getCode().equals("S10000")) {

		} else {
			throw new IllegalArgumentException(rtn.getMsg() + ":" + rtn.getSubMsg());
		}

		if (rtn.getCode().equals("S10000")) {// 处理成功
			if (rtn.getBizContent() != null) {
				PaymentToCardRes bizContent = JsonMapUtil.gsonToObj(JsonMapper.toJsonString(rtn.getBizContent()),
						PaymentToCardRes.class);
				if (bizContent.getStatus().equals("F")) {// 订单状态，S-成功；P-处理中；F-失败
					resultVO.setCode(ErrInfo.ERROR.getCode());
					resultVO.setMsg("订单处理失败,订单状态：F");
				} else {
					resultVO.setMsg("订单请求成功，订单状态：" + bizContent.getStatus());
				}
			}

		} else {
			resultVO.setCode(ErrInfo.ERROR.getCode());
			resultVO.setMsg(String.format("网关返回码：%s;网关返回码描述%s;业务返回码：%s;业务返回码描述%s", rtn.getCode(), rtn.getMsg(),
					rtn.getSubCode(), rtn.getSubMsg()));
		}

		return resultVO;

	}

	/**
	 * 银行卡代扣
	 */
	@Override
	public WithholdResultVO tradeBankWitholding(String tradeNo, String amount, CustUserVO custUserVO) {

		WithholdResultVO withholdResultVO = new WithholdResultVO();
		withholdResultVO.setTransId(tradeNo);
		withholdResultVO.setTradeDate(DateUtils.formatDate(new Date(), DateUtils.FORMAT_INT_MINITE));

		BankWitholdingReq bankWitholdingReq = new BankWitholdingReq();
		bankWitholdingReq.setOutTradeNo(tradeNo);

		bankWitholdingReq.setBankAccountName(custUserVO.getRealName());
		bankWitholdingReq.setPhoneNum(custUserVO.getMobile());

		bankWitholdingReq.setCertificatesType("01");
		bankWitholdingReq.setCertificatesNumber(custUserVO.getIdNo());
		bankWitholdingReq.setBankCardNo(custUserVO.getCardNo());
		bankWitholdingReq.setBankCode(custUserVO.getBankCode());

		bankWitholdingReq.setPayableAmount(amount);
		bankWitholdingReq.setCurrency("CNY");
		bankWitholdingReq.setPayeeIdentity(KjtpayUtil.partnerId);

		bankWitholdingReq.setBizProductCode("20204");
		bankWitholdingReq.setPayProductCode("77");

		// bankWitholdingReq.setNotifyUrl(Global.getConfig("kjtpay.tradeBankWitholdingNotifyUrl"));

		RequestBase requestBase = KjtpayUtil.genRequestBase("trade_bank_witholding", bankWitholdingReq);
		String reqString = JsonMapUtil.gsonToJson(requestBase);

		Map<String, String> params = (Map) JsonMapper.fromJsonString(reqString, Map.class);

		// 参数需要进行两次编码
		for (Entry<String, String> entry : params.entrySet()) {
			entry.setValue(URLEncoder.encode(entry.getValue()));
		}

		logger.info("{}-{}-请求地址：{}", "快捷通", "代扣", KjtpayUtil.url);
		logger.info("{}-{}-请求报文：{}", "快捷通", "代扣", reqString);
		String responseString = HttpUtils.postForJson(KjtpayUtil.url, params);
		logger.info("{}-{}-应答结果：{}", "快捷通", "代扣", responseString);
		ResponseParameter rtn = JsonMapUtil.gsonToObj(responseString, ResponseParameter.class);

		// VerifyResult verifyResult = KjtpayUtil.verify(rtn);
		// if (!verifyResult.isSuccess()) {// 验签失败
		// logger.info("验签失败");
		// withholdResultVO.setCode(rtn.getCode());
		// withholdResultVO.setMsg(rtn.getMsg());
		// withholdResultVO.setSuccess(false);
		//
		// }else

		if (rtn.getCode().equals("S10000")) {// 处理成功
			if (rtn.getBizContent() != null) {
				SingleTradeQueryDetail bizContent = JsonMapUtil.gsonToObj(JsonMapper.toJsonString(rtn.getBizContent()),
						SingleTradeQueryDetail.class);
				if (bizContent.getStatus().equals("F")) {// 订单状态，S-成功；P-处理中；F-失败
					withholdResultVO.setSuccess(false);
					withholdResultVO.setCode("F");
					withholdResultVO.setMsg("失败");
				} else {
					withholdResultVO.setSuccess(false);
					withholdResultVO.setCode("P");
					withholdResultVO.setMsg("处理中");
				}
				withholdResultVO.setTransNo(bizContent.getTradeNo());
			}

		} else {
			withholdResultVO.setCode(rtn.getCode());
			// withholdResultVO.setMsg(rtn.getMsg()+":"+rtn.getSubMsg());
			withholdResultVO.setMsg(rtn.getSubMsg());
			withholdResultVO.setSuccess(false);
		}

		return withholdResultVO;
	}

	@Override
	public void queryTransferToCard(String tradeNo) {
		// TODO Auto-generated method stub

		SingleTradeQueryReq singleTradeQueryReq = new SingleTradeQueryReq();
		singleTradeQueryReq.setOutTradeNo(tradeNo);

		RequestBase requestBase = KjtpayUtil.genRequestBase("trade_query", singleTradeQueryReq);
		String reqString = JsonMapUtil.gsonToJson(requestBase);

		Map<String, String> params = (Map) JsonMapper.fromJsonString(reqString, Map.class);

		// 参数需要进行两次编码
		for (Entry<String, String> entry : params.entrySet()) {
			entry.setValue(URLEncoder.encode(entry.getValue()));
		}

		logger.info("{}-{}-请求地址：{}", "快捷通", "代扣查询", KjtpayUtil.url);
		logger.info("{}-{}-请求报文：{}", "快捷通", "代扣查询", reqString);
		String responseString = HttpUtils.postForJson(KjtpayUtil.url, params);
		logger.info("{}-{}-应答结果：{}", "快捷通", "代扣查询", responseString);
		ResponseParameter rtn = JsonMapUtil.gsonToObj(responseString, ResponseParameter.class);

		// VerifyResult verifyResult = KjtpayUtil.verify(rtn);
		//
		// if (!verifyResult.isSuccess()) {// 验签失败
		// throw new IllegalArgumentException("验签失败");
		// }
		// 处理成功
		if (rtn.getCode().equals("S10000")) {
			SingleTradeQueryDetail detail = JsonMapUtil.gsonToObj(JsonMapper.toJsonString(rtn.getBizContent()),
					SingleTradeQueryDetail.class);
			if (detail.getStatus().equals("submitted")) {// 提交银行成功

			} else if (detail.getStatus().equals("success")) {// 出款成功

			} else if (detail.getStatus().equals("failed")) {// 出款失败

			}
		} else {
			throw new IllegalArgumentException(rtn.getMsg() + ":" + rtn.getSubMsg());
		}
	}

	@Override
	public WithholdQueryResultVO queryTradeBankWitholding(String tradeNo) {
		// TODO Auto-generated method stub
		WithholdQueryResultVO rtnVo = new WithholdQueryResultVO();
		SingleTradeQueryReq singleTradeQueryReq = new SingleTradeQueryReq();
		singleTradeQueryReq.setOutTradeNo(tradeNo);

		RequestBase requestBase = KjtpayUtil.genRequestBase("trade_query", singleTradeQueryReq);
		String reqString = JsonMapUtil.gsonToJson(requestBase);

		Map<String, String> params = (Map) JsonMapper.fromJsonString(reqString, Map.class);

		// 参数需要进行两次编码
		for (Entry<String, String> entry : params.entrySet()) {
			entry.setValue(URLEncoder.encode(entry.getValue()));
		}

		logger.info("{}-{}-请求地址：{}", "快捷通", "转账到银行卡查询", KjtpayUtil.url);
		logger.info("{}-{}-请求报文：{}", "快捷通", "转账到银行卡查询", reqString);
		String responseString = HttpUtils.postForJson(KjtpayUtil.url, params);
		logger.info("{}-{}-应答结果：{}", "快捷通", "转账到银行卡查询", responseString);
		ResponseParameter rtn = JsonMapUtil.gsonToObj(responseString, ResponseParameter.class);

		// VerifyResult verifyResult = KjtpayUtil.verify(rtn);
		//
		// if (!verifyResult.isSuccess()) {// 验签失败
		// throw new IllegalArgumentException("验签失败");
		// }
		// 处理成功
		if (rtn.getCode().equals("S10000")) {
			SingleTradeQueryDetail detail = JsonMapUtil.gsonToObj(JsonMapper.toJsonString(rtn.getBizContent()),
					SingleTradeQueryDetail.class);
			// if(detail.getStatus().equals("TRADE_CLOSED")){//未付款交易超时关闭，或支付完成后全额退款
			//
			// }else
			// if(detail.getStatus().equals("TRADE_SUCCESS")){//交易支付成功，不可退款
			//
			// }else if(detail.getStatus().equals("TRADE_FINISHED")){//交易结束
			//
			// }

			rtnVo.setCode(detail.getStatus());
			if (StringUtils.isNotBlank(detail.getAmount())) {
				rtnVo.setSuccAmt(MoneyUtils.yuan2fen(detail.getAmount()));
			}
			if (StringUtils.isNotBlank(detail.getModifyTime())) {
				Date tradeDate = DateUtils.parse(detail.getModifyTime(), DateUtils.FORMAT_LONG);
				rtnVo.setOrigTradeDate(DateUtils.formatDate(tradeDate, DateUtils.FORMAT_INT_MINITE));
			}

			rtnVo.setMsg("成功");
			if ("TRADE_CLOSED".equals(rtnVo.getCode())) {
				rtnVo.setMsg(rtn.getSubMsg());
			}
		} else {
			rtnVo.setCode("SYS_ERR");
			rtnVo.setMsg(rtn.getMsg() + ":" + rtn.getSubMsg());
		}

		return rtnVo;
	}

	// @Override
	// public String transferToCardNotify(TransferToCardNotifyOP op) {
	// // TODO Auto-generated method stub
	// VerifyResult verifyResult = KjtpayUtil.verify(BeanMapper.describe(op));
	//
	// if (!verifyResult.isSuccess()) {// 验签失败
	// return "验签失败";
	// }
	//
	// if(op.getWithdrawal_status().equals("WITHDRAWAL_SUCCESS")){//转账成功
	//
	// }else if(op.getWithdrawal_status().equals("WITHDRAWAL_FAIL")){//转账失败
	//
	// }else if(op.getWithdrawal_status().equals("RETURN_TICKET")){//转账退票
	//
	// }
	//
	//
	//
	// return "success";
	// }
	// @Override
	// public String tradeBankWitholdingNotify(TradeBankWitholdingNotifyOP op) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}

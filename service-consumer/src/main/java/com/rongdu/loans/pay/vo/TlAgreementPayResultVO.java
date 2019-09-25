package com.rongdu.loans.pay.vo;

import com.rongdu.common.exception.ErrInfo;

import java.io.Serializable;

/**
 * 通联
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/8
 *
 */
public class TlAgreementPayResultVO implements Serializable {

	private static final long serialVersionUID = -5809672592176488983L;

	/**
	 * 是否成功
	 */
	private Boolean success = false;
	/**
	 * 应答码
	 */
	private String resCode;
	/**
	 * 应答信息
	 */
	private String resMessage;
	/**
	 * 商户号
	 */
	private String merchantId;

	/**
	 * 商户订单号
	 */
	private String merchantNo;

	/**
	 * 交易订单号
	 */
	private String tradeNo;

	/**
	 * 订单状态：I（支付处理中）S（支付成功）F（支付失败）
	 */
	private String status;

	/**
	 * 交易完成时间（格式：YYYYMMDDhhmmss 订单状态为终态S或F时存在）
	 */
	private String tradeTime;

	/**
	 * 保留域（商户保留域原样回传）
	 */
	private String memo;

	/**
	 * 金额（分）
	 */
	private String amount;
	/**
	 * 金额（元）
	 */
	private String amountYuan;

	/**
	 * 币种
	 */
	private String transCur;

	/**
	 * 签约号
	 */
	private String contractNo;

	public TlAgreementPayResultVO(){
		this.resCode = ErrInfo.ERROR.getCode();
		this.resMessage = ErrInfo.ERROR.getMsg();
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return "S".equals(getStatus());
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMessage() {
		return resMessage;
	}

	public void setResMessage(String resMessage) {
		this.resMessage = resMessage;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransCur() {
		return transCur;
	}

	public void setTransCur(String transCur) {
		this.transCur = transCur;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getAmountYuan() {
		return amountYuan;
	}

	public void setAmountYuan(String amountYuan) {
		this.amountYuan = amountYuan;
	}


}

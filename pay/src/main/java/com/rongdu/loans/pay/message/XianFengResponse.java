package com.rongdu.loans.pay.message;

import com.rongdu.loans.credit.common.CreditApiVo;

/**  
* @Title: XianFengResponse.java  
* @Description: 先锋代扣响应类 
* @author: yuanxianchu  
* @date 2018年6月4日  
* @version V1.0  
*/
public class XianFengResponse extends CreditApiVo {
	
	private static final long serialVersionUID = 2615399177254390318L;
	/**
	 * 应答代码
	 */
	private String resCode;
	/**
	 * 应答消息
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
	 *交易完成时间（格式：YYYYMMDDhhmmss 订单状态为终态S或F时存在）
	 */
	private String tradeTime;
	
	/**
	 *保留域（商户保留域原样回传）
	 */
	private String memo;
	
	/**
	 *订单签名数据
	 */
	private String sign;
	
	/**
	 * 代扣成功金额（分）
	 */
	private String amount;
	
	/**
	 * 币种
	 */
	private String transCur;
	
	

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	@Override
	public boolean isSuccess() {
		return "S".equals(getStatus());
	}

	@Override
	public void setSuccess(boolean success) {

	}

	@Override
	public String getCode() {
		return getResCode();
	}

	@Override
	public void setCode(String code) {

	}

	@Override
	public String getMsg() {
		return getResMessage();
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "XianFengResponse [resCode=" + resCode + ", resMessage=" + resMessage + ", merchantId=" + merchantId
				+ ", merchantNo=" + merchantNo + ", tradeNo=" + tradeNo + ", status=" + status + ", tradeTime="
				+ tradeTime + ", memo=" + memo + ", sign=" + sign + ", amount=" + amount + ", transCur=" + transCur
				+ "]";
	}
	
	

}

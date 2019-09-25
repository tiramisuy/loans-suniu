package com.rongdu.loans.pay.xianfeng.vo;

import java.io.Serializable;

/**
 * @author liuzhuang
 * 
 */
public class XfAgreementPayDataVO implements Serializable {

	private static final long serialVersionUID = -5103606434948118357L;

	/**
	 * 商户订单号
	 */
	private String merchantNo = "AP" + System.nanoTime();
	/**
	 * 签约号
	 */
	private String contractNo;
	/**
	 * 金额 单位分
	 */
	private String amount;
	/**
	 * 币种,由先锋支付定义，商户传入固定值：156（表示人民币）
	 */
	private String transCur = "156";
	/**
	 * 证件类型 由先锋支付定义,商户传入固定值：0(表示身份证)
	 */
	private String certificateType = "0";
	/**
	 * 证件号码
	 */
	private String certificateNo;
	/**
	 * 银行卡号
	 */
	private String accountNo;
	/**
	 * 持卡人姓名
	 */
	private String accountName;
	/**
	 * 手机号
	 */
	private String mobileNo;

	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 订单超时时间
	 */
	private String expireTime;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}

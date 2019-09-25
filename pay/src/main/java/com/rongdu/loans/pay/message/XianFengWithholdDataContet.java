package com.rongdu.loans.pay.message;

import java.io.Serializable;

/**  
* @Title: XianFengWithholdDataContet.java  
* @Description: 先锋代扣 业务数据 
* @author: yuanxianchu  
* @date 2018年6月4日  
* @version V1.0  
*/
public class XianFengWithholdDataContet implements Serializable {
	
	
	private static final long serialVersionUID = -6094516600130149818L;

	/**
	 * 商户订单号
	 */
	private String merchantNo;
	
	/**
	 * 金额
	 */
	private String amount;
	
	/**
	 * 币种
	 */
	private String transCur;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 账户类型
	 */
	private String accountType;
	
	/**
	 * 账户号
	 */
	private String accountNo;
	
	/**
	 * 账户名称
	 */
	private String accountName;
	
	/**
	 * 银行编码
	 */
	private String bankId;
	
	/**
	 * 证件类型
	 */
	private String certificateType;
	
	/**
	 * 证件号码
	 */
	private String certificateNo;
	
	/**
	 * 手机号
	 */
	private String mobileNo;
	
	/**
	 * 开户省
	 */
	private String branchProvince;
	
	/**
	 * 开户市
	 */
	private String branchCity;
	
	/**
	 * 开户支行名称
	 */
	private String branchName;
	
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 商品信息
	 */
	private String productInfo;
	
	/**
	 * 后台通知地址
	 */
	private String noticeUrl;
	
	/**
	 * 订单超时时间
	 */
	private String expireTime;
	
	/**
	 * 保留域
	 */
	private String memo;
	
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getBranchProvince() {
		return branchProvince;
	}

	public void setBranchProvince(String branchProvince) {
		this.branchProvince = branchProvince;
	}

	public String getBranchCity() {
		return branchCity;
	}

	public void setBranchCity(String branchCity) {
		this.branchCity = branchCity;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}

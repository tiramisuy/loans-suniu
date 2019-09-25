package com.rongdu.loans.pay.op;

import java.io.Serializable;

import com.rongdu.common.utils.DateUtils;

/**  
 * code y0602
* @Title: XfWithholdOP.java  
* @Description: 先锋支付入参对象 
* @author: yuanxianchu  
* @date 2018年6月2日  
* @version V1.0  
*/
public class XfWithholdOP implements Serializable {

	private static final long serialVersionUID = -5103606434948118357L;
	
	/**
	 * 商户订单号
	 */
	private String merchantNo = "XF" + System.nanoTime();;
	
	/**
	 * 金额(分)
	 */
	private String amount;
	
	/**
	 * 币种(由先锋支付定义，商户传入固定值：156（人民币）)
	 */
	private String transCur;
	
	/**
	 * 用户类型(由先锋支付定义，商户传入固定值：1（对私）)
	 */
	private String userType;
	
	/**
	 * 账户类型(由先锋支付定义，商户传入指定值：1（借记卡）)
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
	 * 证件类型(由先锋支付定义，商户传入固定值：0（身份证）)
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
	 * 订单超时时间(
	 * 格式：YYYY-MM-DDhh:mm:ss，为空则使用先锋支付平台
	 * 默认超时时间（交易申请时间起7天）)
	 */
	private String expireTime;
	
	/**
	 * 保留域(商户保留域原样回传)
	 */
	private String memo;
	
	
	private String userId;
	
	private String contractId;
	
	private String applyId;
	
	/**
	 * 订单请求时间
	 */
	private String tradeTime = DateUtils.getDate("yyyyMMddHHmmss");
	
	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

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

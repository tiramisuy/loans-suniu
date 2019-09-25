package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2017/6/27.
 */
public class IdentityInfoOP implements Serializable {
	// 序列号
	private static final long serialVersionUID = -5360657633358261437L;

	/**
	 * 真实姓名
	 */
	@NotBlank(message = "真实姓名不能为空")
	private String trueName;

	/**
	 * 证件类型
	 */
	private String idType;

	/**
	 * 证件号
	 */
	@NotBlank(message = "证件号不能为空")
	private String idNo;

	/**
	 * 银行预留手机号
	 */
	private String bankMobile;

	/**
	 * 银行编号
	 */
	@NotBlank(message = "银行编号不能为空")
	private String bankCode;

	/**
	 * 银行卡号
	 */
	@NotBlank(message = "银行卡号不能为空")
	private String cardNo; // 银行卡号

	/**
	 * 账户
	 */
	@NotBlank(message = "账户不能为空")
	private String account;

	/**
	 * 短信验证码
	 */
	private String msgVerCode;

	/**
	 * 产品ID
	 */
	@NotBlank(message = "产品ID不能为空")
	private String productId;

	/**
	 * 来源（1-ios, 2-android, 3-h5, 4-api）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 申请编号
	 */
	private String applyId;

	/**
	 * 电子账户
	 */
	private String accountId;
	/**
	 * 第三方支付绑定id、unique_code
	 */
	private String bindId;

	/**
	 * 开户行所在地址
	 */
	@NotBlank(message = "开户行所在地址不能为空")
	private String cityId;

	// 用户昵称
	private String uname;

	private String orderNo;

	/**
	 * 协议绑卡号
	 */
	private String protocolNo;

	/**
	 * 邮箱
	 */
	private String email;

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMsgVerCode() {
		return msgVerCode;
	}

	public void setMsgVerCode(String msgVerCode) {
		this.msgVerCode = msgVerCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

}

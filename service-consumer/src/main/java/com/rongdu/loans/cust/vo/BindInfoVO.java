package com.rongdu.loans.cust.vo;

import java.io.Serializable;

public class BindInfoVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 2456243409699774799L;
    
	private String bankCode;	// 银行代码
	private String bankName;	// 银行名称
	private String bankCityId;	// 开户行地址Id
	private String bankCityName;	// 开户行地址名称
	private String cardNo;		// 银行卡号
	private String accountId;	// 恒丰银行电子账户
	private String bindId;		// 第三方支付绑定id
    private String userId;      // 用户id
	private String name;		// 用户昵称
	private String realName;	// 真实姓名
	private String idType;		// 用户证件类型
	private String idNo;		// 用户证件号码
	private String mobile;		// 手机号码
	private String email;		// 用户邮箱
	private Integer emailBind;	// 是否绑定邮箱：0未绑定1已绑定
	private String termsAuthId;     // 四合一授权id
	private String avatar;		// 用户头像
	private Integer sex;		// 用户性别
	private String birthday;	// 生日
	private String qq;	        // qq
	private String alipayId;	// 支付宝账户
	private String weixinOpenid;// 微信
	private Integer identityStatus;  // 身份认证状态
	
	public String getBankCode() {
		return bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public String getAccountId() {
		return accountId;
	}
	public String getUserId() {
		return userId;
	}
	public String getName() {
		return name;
	}
	public String getRealName() {
		return realName;
	}
	public String getIdType() {
		return idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public String getEmail() {
		return email;
	}
	public Integer getEmailBind() {
		return emailBind;
	}
	public String getAvatar() {
		return avatar;
	}
	public Integer getSex() {
		return sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getQq() {
		return qq;
	}
	public String getAlipayId() {
		return alipayId;
	}
	public String getWeixinOpenid() {
		return weixinOpenid;
	}
	public Integer getIdentityStatus() {
		return identityStatus;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setEmailBind(Integer emailBind) {
		this.emailBind = emailBind;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}
	public void setWeixinOpenid(String weixinOpenid) {
		this.weixinOpenid = weixinOpenid;
	}
	public void setIdentityStatus(Integer identityStatus) {
		this.identityStatus = identityStatus;
	}
	public String getBindId() {
		return bindId;
	}
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	public String getTermsAuthId() {
		return termsAuthId;
	}
	public void setTermsAuthId(String termsAuthId) {
		this.termsAuthId = termsAuthId;
	}
	@Deprecated
	public void setTruename(String realName) {
		this.realName = realName;
	}
	@Deprecated
	public String getTruename() {
		return this.realName;
	}
	public String getBankCityName() {
		return bankCityName;
	}
	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}
	public String getBankCityId() {
		return bankCityId;
	}
	public void setBankCityId(String bankCityId) {
		this.bankCityId = bankCityId;
	}
	
}

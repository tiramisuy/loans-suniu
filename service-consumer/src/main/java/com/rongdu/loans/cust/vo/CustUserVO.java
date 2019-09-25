package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表映射数据
 * 
 * @author likang
 * 
 */
public class CustUserVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6217468386817584730L;

	private String id;
	private String name; // 用户昵称
	private String realName; // 真实姓名
	private String idType; // 用户证件类型
	private String idNo; // 用户证件号码
	private String bankCode; // 银行代码
	private String cardNo; // 银行卡号
	private String bankMobile;// 银行预留手机号
	private String bankCityId; // 开户银行地址
	private String mobile; // 手机号码
	private String email; // 用户邮箱
	private Integer emailBind; // 是否绑定邮箱：0未绑定1已绑定
	private String avatar; // 用户头像
	private Integer sex; // 用户性别
	private String birthday; // 生日
	private String password; // 用户密码
	private String paypwd; // 支付密码
	private String qq; // qq
	private String alipayId; // 支付宝账号
	private String xuexinId; // 学信网账号
	private String weixinOpenid; // 微信互联openid
	private Integer loginNum; // 登录次数
	private Date registerTime; // 用户注册时间
	private Date loginTime; // 当前登录时间
	private Date lastLoginTime; // 上次登录时间
	private String loginIp; // 当前登录ip
	private String lastLoginIp; // 上次登录ip
	private String type; // 用户类型
	private String level; // 用户等级
	private Integer points; // 用户积分
	private String bindId; // 第三方支付绑定id
	private String accountId; // 电子账户
	private String kdAccountId; // 口袋电子账户
	private Integer hjsAccountId; // 汉金所电子账户
	private String identityStatus; // 身份认证状态
	private Double availableBalance; // 可用余额
	private Double frozenBalance; // 冻结金额
	private Double creditLine; // 授信额度
	private Double availCreditLine; // 可用授信额度
	private Double usedCreditLine; // 已用授信额度
	private Integer status; // 用户状态：1-正常，0-锁定账户
	private Integer exppoints; // 用户经验值
	private Integer source; // 来源（1-ios,2-android）
	private String channel; // 渠道
	private Integer inviterId; // 邀请人ID
	private String remark; // 备注信息 传入门店id

	private String protocolNo; // 协议绑卡号

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEmailBind() {
		return emailBind;
	}

	public void setEmailBind(Integer emailBind) {
		this.emailBind = emailBind;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getWeixinOpenid() {
		return weixinOpenid;
	}

	public void setWeixinOpenid(String weixinOpenid) {
		this.weixinOpenid = weixinOpenid;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(Double frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	public Double getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(Double creditLine) {
		this.creditLine = creditLine;
	}

	public Double getAvailCreditLine() {
		return availCreditLine;
	}

	public void setAvailCreditLine(Double availCreditLine) {
		this.availCreditLine = availCreditLine;
	}

	public Double getUsedCreditLine() {
		return usedCreditLine;
	}

	public void setUsedCreditLine(Double usedCreditLine) {
		this.usedCreditLine = usedCreditLine;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getExppoints() {
		return exppoints;
	}

	public void setExppoints(Integer exppoints) {
		this.exppoints = exppoints;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getInviterId() {
		return inviterId;
	}

	public void setInviterId(Integer inviterId) {
		this.inviterId = inviterId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdentityStatus() {
		return identityStatus;
	}

	public void setIdentityStatus(String identityStatus) {
		this.identityStatus = identityStatus;
	}

	public void filterSensitiveData() {
		this.password = null;
		this.paypwd = null;
		this.registerTime = null;
		this.loginTime = null;
		this.lastLoginTime = null;
		this.loginIp = null;
		this.lastLoginIp = null;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getXuexinId() {
		return xuexinId;
	}

	public void setXuexinId(String xuexinId) {
		this.xuexinId = xuexinId;
	}

	public String getBankCityId() {
		return bankCityId;
	}

	public void setBankCityId(String bankCityId) {
		this.bankCityId = bankCityId;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getKdAccountId() {
		return kdAccountId;
	}

	public void setKdAccountId(String kdAccountId) {
		this.kdAccountId = kdAccountId;
	}

	public Integer getHjsAccountId() {
		return hjsAccountId;
	}

	public void setHjsAccountId(Integer hjsAccountId) {
		this.hjsAccountId = hjsAccountId;
	}


}

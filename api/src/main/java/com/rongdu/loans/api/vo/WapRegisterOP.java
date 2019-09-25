package com.rongdu.loans.api.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class WapRegisterOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4277008632417170841L;

	@NotBlank(message = "用户名不能为空")
	private String account;

	private String password;

	@NotBlank(message = "短息验证码不能为空")
	private String msgVerCode;

	private String inviteCode;

	private String ip;
	/**
	 * 用户姓名
	 */
	private String realName;

	/*
	 * 门店id 存入custuser表remark字段.
	 */
	private String companyId;
	/*
	 * 组id
	 */
	private String groupId;
	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */
	// @NotBlank(message="进件来源不能为空")
	// @Pattern(regexp="1|2|3|4",message="进件来源类型有误")
	private String source;

	/**
	 * 渠道代码
	 */
	private String channel;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsgVerCode() {
		return msgVerCode;
	}

	public void setMsgVerCode(String msgVerCode) {
		this.msgVerCode = msgVerCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	

}

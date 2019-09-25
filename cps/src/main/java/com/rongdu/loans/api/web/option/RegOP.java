package com.rongdu.loans.api.web.option;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class RegOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6366014216178008764L;

	/**
	 * 手机号
	 */
	@NotBlank(message="手机号不能为空")
	private String account;
	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空")
	private String password;
	/**
	 * 短息验证码
	 */
	@NotBlank(message="短息验证码不能为空")
	private String msgVerCode;
	
	/**
	 * 渠道代码
	 */
	@NotBlank(message="渠道码不能为空")
	@Pattern(regexp="LVKA|BEIKE|JIEDAITONG|JUQIANBAO",message="渠道码有误")
	private String channel;

	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	private String source;
	
	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getMsgVerCode() {
		return msgVerCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMsgVerCode(String msgVerCode) {
		this.msgVerCode = msgVerCode;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}

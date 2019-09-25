package com.rongdu.loans.cust.option;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class ForgetPwdOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4277008632417170841L;
	
	@NotBlank(message="用户名不能为空")
	private String account;
	
	@NotBlank(message="短息验证码不能为空")
	private String msgVerCode;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMsgVerCode() {
		return msgVerCode;
	}
	public void setMsgVerCode(String msgVerCode) {
		this.msgVerCode = msgVerCode;
	}
}

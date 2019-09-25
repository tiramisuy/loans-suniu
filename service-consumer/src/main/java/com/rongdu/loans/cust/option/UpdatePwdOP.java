package com.rongdu.loans.cust.option;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class UpdatePwdOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5450867343408759224L;
	
	@NotBlank(message="用户名不能为空")
	private String account;
	
	@NotBlank(message="密码不能为空")
	private String password;

	/**
	 * 原密码
	 */
	private String oldPwd;
	
	/**
	 * 修改密钥
	 */
	private String updateToken;
	
	/**
	 * 修改时间
	 */
	private Date UpdateTime;

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

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getUpdateToken() {
		return updateToken;
	}

	public void setUpdateToken(String updateToken) {
		this.updateToken = updateToken;
	}

	public Date getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(Date updateTime) {
		UpdateTime = updateTime;
	}
}

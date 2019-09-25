package com.rongdu.loans.api.vo;

import java.io.Serializable;

public class OpenAccountVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7381878047328161533L;
	
	/**
	 * 电子账户
	 */
	private String accountId;
	
	/**
	 * 快捷支付绑定id
	 */
	private String bindId;
	
	/**
	 * 信息保存结果
	 */
	private Integer saveResult;
	
	private Integer isNewUser;

	public String getAccountId() {
		return accountId;
	}

	public String getBindId() {
		return bindId;
	}

	public Integer getSaveResult() {
		return saveResult;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public void setSaveResult(Integer saveResult) {
		this.saveResult = saveResult;
	}

	public Integer getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
	} 
}

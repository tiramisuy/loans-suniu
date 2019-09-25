package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

public class AuthQueryResultVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4523216628308906486L;
	/**
	 * 响应代码
	 */
	private String retCode;
	/**
	 * 响应描述
	 */
	private String retMsg;
	/**
	 * 电子账号
	 */
	private String accountId;
	/**
	 * 姓名
	 */
	private String name;

	/**
	 * attrFlags
	 */
	private String attrFlags;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttrFlags() {
		return attrFlags;
	}

	public void setAttrFlags(String attrFlags) {
		this.attrFlags = attrFlags;
	}
}

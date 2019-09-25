package com.rongdu.loans.baiqishi.message;

import java.io.Serializable;

/**
 * 查询芝麻信用授权结果-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeResultResultData implements Serializable{
	
	private static final long serialVersionUID = -7271155646594153079L;

	/**
	 *查询是否成功  true, false
	 */
	private boolean success;
	/**
	 * 是否授权  true, false
	 */
	private boolean authorized;
	/**
	 * 用户  openId
	 */
	private String openId;
	/**
	 * 芝麻信用 调用错误码
	 */
	private String errorCode;
	/**
	 * 芝麻信用调用错误信息
	 */
	private String errorMessage; 
	
	public AuthorizeResultResultData(){
		
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

	
}


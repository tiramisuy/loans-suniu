package com.rongdu.loans.baiqishi.message;

import java.io.Serializable;

/**
 * 芝麻信用授权-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeResultData implements Serializable{
	
	private static final long serialVersionUID = -7271155646594153079L;
	/**
	 *成功与否
	 */
	private  String	success;
	/**
	 * 芝麻信用认证界面
	 */
	private  String	authInfoUrl;
	
	public AuthorizeResultData(){
		
	}
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getAuthInfoUrl() {
		return authInfoUrl;
	}

	public void setAuthInfoUrl(String authInfoUrl) {
		this.authInfoUrl = authInfoUrl;
	}
	
}


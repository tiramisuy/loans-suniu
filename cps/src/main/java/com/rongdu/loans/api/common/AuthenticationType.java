package com.rongdu.loans.api.common;

/**
 * 认证类型
 * @author likang
 *
 */
public enum AuthenticationType {
	
	/**
	 * 用户密码登录认证
	 */
	LOGIN("1", "登录认证"), 
	/**
	 * 签名认证
	 */
//	SIGN("3", "签名认证"),
	/**
	 *   token认证
	 */
	TOKEN("2", "token认证");

	private String code;
	private String msg;

	AuthenticationType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

package com.rongdu.loans.credit100.message;

/**
 * 百融金服-用户登录-应答结果
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class Credit100LoginResponse {

	/**
	 * 登录唯一标示
	 */
	private String tokenid = "";
	/**
	 *响应代码
	 */
	private String code = "";

	public Credit100LoginResponse() {
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}

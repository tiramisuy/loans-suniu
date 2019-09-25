package com.rongdu.loans.tongrong.vo;

import java.io.Serializable;

public class TRPayVO implements Serializable{

	private static final long serialVersionUID = -3815988992296554250L;

	/**
	 * 返回状态码
	 */
	private String code;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 附加信息
	 */
	private Object data;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}

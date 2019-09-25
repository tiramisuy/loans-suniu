package com.rongdu.loans.common;

import java.io.Serializable;


public class DubboResponse implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2741134216622114972L;
	
	/**
	 * 默认成功返回码
	 */
	public static final String SUCC_CODE = "0000";
	/**
	 * 默认成功返回信息
	 */
	public static final String SUCC_MSG = "success";

	/**
	 * 返回码
	 */
	private String code;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	public DubboResponse() {
		this.code = SUCC_CODE;
		this.message = SUCC_MSG;
	}

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
	
}

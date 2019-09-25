package com.rongdu.loans.hanjs.vo;

import java.io.Serializable;

public class HanJSResultVO implements Serializable{

	private static final long serialVersionUID = -6622106989939455680L;

	/**
	 * 返回响应码
	 */
	private String code;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	private Object data;

	
	public HanJSResultVO() {
		super();
	}
	
	public HanJSResultVO(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public HanJSResultVO(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}

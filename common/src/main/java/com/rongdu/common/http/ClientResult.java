package com.rongdu.common.http;

import java.io.Serializable;

public class ClientResult implements Serializable{
	
	private String code;
	
	private String message;

	private  String res;
	
	private Object result;

	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.message = res;
	}
}

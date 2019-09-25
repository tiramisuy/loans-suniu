package com.rongdu.loans.common;

import java.io.Serializable;

/**
 *
 */
public class WebResult implements Serializable{
	
	private static final long serialVersionUID = 6059789383786071296L;
	
	private String code;
	private String msg;
	private Object data;
	

	
	public WebResult(String code, String msg){
		this.code = code;
		this.msg =msg;
	}

	public WebResult(String code, String msg, Object data){
		this.code = code;
		this.msg =msg;
		this.data = data;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

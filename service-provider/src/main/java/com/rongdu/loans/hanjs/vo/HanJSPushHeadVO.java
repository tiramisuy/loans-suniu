package com.rongdu.loans.hanjs.vo;

import java.io.Serializable;

public class HanJSPushHeadVO implements Serializable{

	private static final long serialVersionUID = -761366146668991644L;

	private String code;
	
	private String msg;
	
	private String retCode;

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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
	
}

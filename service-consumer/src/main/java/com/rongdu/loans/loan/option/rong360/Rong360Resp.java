package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

public class Rong360Resp implements Serializable {

	private static final long serialVersionUID = 345468739282512101L;
	/**
	 * 服务器响应编号
	 */
	private String code;
	/**
	 * 服务器响应消息
	 */
	private String msg;
	/**
	 * 服务器响应结果对象
	 */
	private Object data;
	
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
	
	@Override
	public String toString() {
		return "AppResponseRongResult [" + (code != null ? "code=" + code + ", " : "")
				+ (msg != null ? "msg=" + msg + ", " : "") + (data != null ? "data=" + data : "") + "]";
	}
}

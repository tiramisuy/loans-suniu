package com.rongdu.loans.entity;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder(value={"code","msg","result"})
public class Response {
	private Integer code = ResponseCodes.success.code;
	private String msg = ResponseCodes.success.msg;
	private Object result;
	
	public Response() {
	}
	public Response(Integer code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Response(Integer code,String msg,Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}

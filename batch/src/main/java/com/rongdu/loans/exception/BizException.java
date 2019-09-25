package com.rongdu.loans.exception;

public class BizException extends Exception{
	
	private String code;
	private static final long serialVersionUID = 64724373333364852L;
	
	public BizException(String code,String message){
		super(message);
		this.code = code;
	}
	
	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

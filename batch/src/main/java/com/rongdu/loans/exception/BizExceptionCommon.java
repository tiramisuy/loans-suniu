package com.rongdu.loans.exception;

public class BizExceptionCommon extends Exception{

	private static final long serialVersionUID = -2339473401353913067L;
	private int code;
	
	public BizExceptionCommon(int code,String message){
		super(message);
		this.code = code;
	}
	
	public BizExceptionCommon(String message) {
		super(message);
	}

	public BizExceptionCommon(Throwable cause) {
		super(cause);
	}

	public BizExceptionCommon(String message, Throwable cause) {
		super(message, cause);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}

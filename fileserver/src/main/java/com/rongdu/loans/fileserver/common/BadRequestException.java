package com.rongdu.loans.fileserver.common;

import org.springframework.validation.Errors;

import com.rongdu.common.exception.ErrInfo;

public class BadRequestException extends RuntimeException{
	
	private String code;
	private Errors errors;
	
	private static final long serialVersionUID = 64724373333364852L;
	
	public BadRequestException() {		
	}

	public BadRequestException(Errors errors) {
		super(ErrInfo.BAD_REQUEST.getMsg());
		this.code = ErrInfo.BAD_REQUEST.getCode();
		this.errors = errors;
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

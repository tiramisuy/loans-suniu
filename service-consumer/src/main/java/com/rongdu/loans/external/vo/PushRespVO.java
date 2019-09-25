package com.rongdu.loans.external.vo;

import java.io.Serializable;

import com.rongdu.common.exception.ErrInfo;

public class PushRespVO implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5770530107181909063L;

	
	/**
     * 返回码 
     */
    private String code;
    
    /**
     * 返回信息  
     */
    private String message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;

public class BlackVO extends CreditApiVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086604470230375752L;
	private String code;
	private String msg;

	private String errorCode;
	private String errorMsg;
	private BlackData data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		if (success) {
			return data.getCode();
		} else {
			return errorCode;
		}
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		if (success) {
			return data.getDesc();
		} else {
			return errorMsg;
		}
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public BlackData getData() {
		return data;
	}

	public void setData(BlackData data) {
		this.data = data;
	}

}

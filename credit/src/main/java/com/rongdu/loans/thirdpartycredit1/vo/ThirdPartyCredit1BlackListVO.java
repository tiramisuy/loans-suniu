package com.rongdu.loans.thirdpartycredit1.vo;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;

public class ThirdPartyCredit1BlackListVO extends CreditApiVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086604470230375752L;
	private String code;
	private String message;
	private String result;

	public boolean isSuccess() {
		if ("S001".equals(getCode())) {
			return true;
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return getMessage();
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void setSuccess(boolean success) {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}

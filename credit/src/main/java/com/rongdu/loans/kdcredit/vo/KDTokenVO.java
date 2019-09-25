package com.rongdu.loans.kdcredit.vo;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;

public class KDTokenVO extends CreditApiVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086604470230375752L;
	private String code;
	private String msg;

	private KDTokenDataDetailVO data;

	public boolean isSuccess() {
		if ("0".equals(getCode())) {
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
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public KDTokenDataDetailVO getData() {
		return data;
	}

	public void setData(KDTokenDataDetailVO data) {
		this.data = data;
	}

	public void setSuccess(boolean success) {

	}

}

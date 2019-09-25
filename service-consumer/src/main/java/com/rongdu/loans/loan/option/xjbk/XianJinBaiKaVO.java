
package com.rongdu.loans.loan.option.xjbk;


import java.io.Serializable;

public class XianJinBaiKaVO implements Serializable {
	private static final long serialVersionUID = 4819370262550068411L;

	public static final int CODE_SUCCESS = 1; // 接口调用成功
	public static final int CODE_FAILURE = 0; // 接口调用失败

	private int status;
	private String message;
	private Object response;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}

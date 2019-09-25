package com.rongdu.loans.pay.exception;
/**  
* @Title: WithholdUpdateException.java
* @Description: 代扣操作成功，更新失败时抛出
* @author: yuanxianchu  
* @date 2018年6月6日  
* @version V1.0  
*/
public class WithholdUpdateException extends RuntimeException {
	
	private static final long serialVersionUID = -4615511931975747773L;
	
	private String code;
	
	private String msg;

	public WithholdUpdateException(String msg) {
		this.msg = msg;
	}
	
	public WithholdUpdateException(String code, String msg) {
		this.code = code;
		this.msg = msg;
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
}

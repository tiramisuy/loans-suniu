package com.rongdu.loans.pay.exception;
/**  
* @Title: XfNewInstanceException.java  
* @Package com.rongdu.loans.pay.exception  
* @author: yuanxianchu  
* @date 2018年6月21日  
* @version V1.0  
*/
public class XfNewInstanceException extends RuntimeException {

	private static final long serialVersionUID = 7461858449099985388L;
	
	private String code;
	
	private String msg;
	
	public XfNewInstanceException(String msg) {
		this.msg = msg;
	}
	
	public XfNewInstanceException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public XfNewInstanceException(String msg,Throwable e) {
		super(e);
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

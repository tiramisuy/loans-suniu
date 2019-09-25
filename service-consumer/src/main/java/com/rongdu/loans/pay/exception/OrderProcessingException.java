package com.rongdu.loans.pay.exception;
/**  
* @Title: OrderProcessingException.java  
* @Package com.rongdu.loans.pay.exception  
* @Description: 订单处理中 
* @author: yuanxianchu  
* @date 2018年6月15日  
* @version V1.0  
*/
public class OrderProcessingException extends RuntimeException{

	private static final long serialVersionUID = 2324632942528807905L;
	
	private String code;
	
	private String msg;

	public OrderProcessingException(String msg) {
		this.msg = msg;
	}
	
	public OrderProcessingException(String code, String msg) {
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

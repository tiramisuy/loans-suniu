package com.rongdu.loans.koudai.vo.create;

import java.io.Serializable;

public class KDCreateResultVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 订单提交状态：
0：处理成功.
其他：处理失败

	 */
	private String code;
	private String order_id;//口袋理财平台订单ID，成功后返回
	private String message;//错误信息，不是必定会返回
	
	public boolean isSuccess() {
		return code.equals("0");
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}

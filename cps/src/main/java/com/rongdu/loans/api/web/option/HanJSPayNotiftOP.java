package com.rongdu.loans.api.web.option;

import java.io.Serializable;

public class HanJSPayNotiftOP implements Serializable{

	private static final long serialVersionUID = -3581501672683149100L;

	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 放款时间
	 */
	private String datetime;
	
	/**
	 * 放款金额
	 */
	private String amount;
	
	/**
	 * 状态码
	 */
	private String code;
	
	/**
	 * 放款信息
	 */
	private String msg;
	
	/**
	 * 秘钥
	 */
	private String sign;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}

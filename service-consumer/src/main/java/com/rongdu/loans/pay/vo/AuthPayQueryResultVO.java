package com.rongdu.loans.pay.vo;

import java.io.Serializable;

public class AuthPayQueryResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String msg;
	private String origTransId;
	private String transNo;
	private String succAmt;
	private String orderStat;

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

	public String getOrigTransId() {
		return origTransId;
	}

	public void setOrigTransId(String origTransId) {
		this.origTransId = origTransId;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(String succAmt) {
		this.succAmt = succAmt;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

}

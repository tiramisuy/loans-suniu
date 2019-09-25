package com.rongdu.loans.tongrong.op;

import java.io.Serializable;

public class TRPayOP implements Serializable{

	private static final long serialVersionUID = 8959915940789718569L;
	/**
	 * 交易号
	 */
	private String requestNo;
	/**
	 * 申请单号
	 */
	private String orderId;
	/**
	 * 用户基本信息
	 */
	private TRUserBasicInfo userBasicInfo;
	/**
	 * 借款订单
	 */
	private TRLoanOrder loanOrder;

	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public TRUserBasicInfo getUserBasicInfo() {
		return userBasicInfo;
	}
	public void setUserBasicInfo(TRUserBasicInfo userBasicInfo) {
		this.userBasicInfo = userBasicInfo;
	}
	public TRLoanOrder getLoanOrder() {
		return loanOrder;
	}
	public void setLoanOrder(TRLoanOrder loanOrder) {
		this.loanOrder = loanOrder;
	}

	
}

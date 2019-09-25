package com.rongdu.loans.loan.option;

import java.io.Serializable;

public class LoanApplySimpleOP implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2350136271753616217L;
	/**
	 * 申请编号
	 */
	private String applyId;
	
	/**
	 * 用户id
	 */
	private String userId;

	public String getApplyId() {
		return applyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

package com.rongdu.loans.loan.option;

import java.io.Serializable;

public class AuthenticationOP implements Serializable {


	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6499768947333286L;
	
	private String userId;  // 用户id
	
	private String applyId; // 申请id

	public String getUserId() {
		return userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
}

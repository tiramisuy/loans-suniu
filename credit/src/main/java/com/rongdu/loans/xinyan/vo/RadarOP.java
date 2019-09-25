package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

public class RadarOP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idNo;
	private String name;
	private String userId;
	private String applyId;

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

}

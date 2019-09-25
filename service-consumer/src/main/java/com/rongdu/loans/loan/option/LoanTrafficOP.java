package com.rongdu.loans.loan.option;

import java.io.Serializable;

public class LoanTrafficOP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	  *产品类型（0-现金贷）
	  */
	private String type;		
	/**
	  *平台名称
	  */
	private String name;		
		
	/**
	  *产品状态(0-初始，1-正常，2-下架)
	  */
	private String status;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}

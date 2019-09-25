package com.rongdu.loans.common;

import com.rongdu.loans.enums.LoanProductEnum;

public class RoleProductConfig {
	private String roleId;
	private LoanProductEnum product;

	RoleProductConfig(String roleId, LoanProductEnum product) {
		this.roleId = roleId;
		this.product = product;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public LoanProductEnum getProduct() {
		return product;
	}

	public void setProduct(LoanProductEnum product) {
		this.product = product;
	}

}

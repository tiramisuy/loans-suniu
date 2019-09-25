package com.rongdu.loans.koudai.op.deposit.user;

import java.io.Serializable;

public class KDOpenAccountQueryOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idNumber;// 身份证

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}

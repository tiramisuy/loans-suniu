package com.rongdu.loans.koudai.op.pay;

import java.io.Serializable;

public class KDPayQueryOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String yur_ref;// 放款订单号，固定30位

	public String getYur_ref() {
		return yur_ref;
	}

	public void setYur_ref(String yur_ref) {
		this.yur_ref = yur_ref;
	}
}

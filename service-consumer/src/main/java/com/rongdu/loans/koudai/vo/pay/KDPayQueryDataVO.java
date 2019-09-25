package com.rongdu.loans.koudai.vo.pay;

import java.io.Serializable;

public class KDPayQueryDataVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String opr_dat;
	private String pay_order_id;

	public String getOpr_dat() {
		return opr_dat;
	}

	public void setOpr_dat(String opr_dat) {
		this.opr_dat = opr_dat;
	}

	public String getPay_order_id() {
		return pay_order_id;
	}

	public void setPay_order_id(String pay_order_id) {
		this.pay_order_id = pay_order_id;
	}

}

package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

public class KDCreateOrderLendPayResultDataVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 订单id
	 */
	private Integer order_id;
	/**
	 * 是否存管 	1是 0否
	 */
	private Integer is_deposit;
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getIs_deposit() {
		return is_deposit;
	}
	public void setIs_deposit(Integer is_deposit) {
		this.is_deposit = is_deposit;
	}
	
}

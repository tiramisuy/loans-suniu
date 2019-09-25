package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;
import java.util.List;

public class TotaldebtResultDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 近1个月共债机构数
	private String current_org_count;
	// 近1个月共债订单数
	private String current_order_count;
	// 最近一个月共债订单的已还款总金金额
	private String current_order_amt;
	// 最近一个月共债订单总金金额
	private String current_order_lend_amt;
	// 历史共债
	private List<TotaldebtHistoryDetail> totaldebt_detail;

	public String getCurrent_org_count() {
		return current_org_count;
	}

	public void setCurrent_org_count(String current_org_count) {
		this.current_org_count = current_org_count;
	}

	public String getCurrent_order_count() {
		return current_order_count;
	}

	public void setCurrent_order_count(String current_order_count) {
		this.current_order_count = current_order_count;
	}

	public String getCurrent_order_amt() {
		return current_order_amt;
	}

	public void setCurrent_order_amt(String current_order_amt) {
		this.current_order_amt = current_order_amt;
	}

	public String getCurrent_order_lend_amt() {
		return current_order_lend_amt;
	}

	public void setCurrent_order_lend_amt(String current_order_lend_amt) {
		this.current_order_lend_amt = current_order_lend_amt;
	}

	public List<TotaldebtHistoryDetail> getTotaldebt_detail() {
		return totaldebt_detail;
	}

	public void setTotaldebt_detail(List<TotaldebtHistoryDetail> totaldebt_detail) {
		this.totaldebt_detail = totaldebt_detail;
	}

}

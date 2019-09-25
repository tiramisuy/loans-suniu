package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

public class TotaldebtHistoryDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 分析该自然月内的共债情况，只支持当前自然月前的6个自然月,格式:YYYYMM
	private String totaldebt_date;
	// 统计周期共债机构数
	private String totaldebt_org_count;
	// 统计周期共债订单数
	private String totaldebt_order_count;
	// 统计周期共债订单的已还款总金金额
	private String totaldebt_order_amt;
	// 统计周期共债订单总金金额
	private String new_or_old;
	// 统计周期共债订单总金金额
	private String totaldebt_order_lend_amt;

	public String getTotaldebt_date() {
		return totaldebt_date;
	}

	public void setTotaldebt_date(String totaldebt_date) {
		this.totaldebt_date = totaldebt_date;
	}

	public String getTotaldebt_org_count() {
		return totaldebt_org_count;
	}

	public void setTotaldebt_org_count(String totaldebt_org_count) {
		this.totaldebt_org_count = totaldebt_org_count;
	}

	public String getTotaldebt_order_count() {
		return totaldebt_order_count;
	}

	public void setTotaldebt_order_count(String totaldebt_order_count) {
		this.totaldebt_order_count = totaldebt_order_count;
	}

	public String getTotaldebt_order_amt() {
		return totaldebt_order_amt;
	}

	public void setTotaldebt_order_amt(String totaldebt_order_amt) {
		this.totaldebt_order_amt = totaldebt_order_amt;
	}

	public String getNew_or_old() {
		return new_or_old;
	}

	public void setNew_or_old(String new_or_old) {
		this.new_or_old = new_or_old;
	}

	public String getTotaldebt_order_lend_amt() {
		return totaldebt_order_lend_amt;
	}

	public void setTotaldebt_order_lend_amt(String totaldebt_order_lend_amt) {
		this.totaldebt_order_lend_amt = totaldebt_order_lend_amt;
	}

}

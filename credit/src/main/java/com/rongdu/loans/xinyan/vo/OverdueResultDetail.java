package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;
import java.util.List;

public class OverdueResultDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String member_count;// 最近6个月内，发⽣生过逾期行行为的机构数量量
	private String order_count;// 最近6个月内，发⽣生过逾期行行为的订单总笔数
	private String debt_amount;// 最近6个月内，发⽣生过逾期行行为的每笔订单的逾期⾦金金额累加；单位:元

	private List<OverdueDetail> details;//逾期详情

	public String getMember_count() {
		return member_count;
	}

	public void setMember_count(String member_count) {
		this.member_count = member_count;
	}

	public String getOrder_count() {
		return order_count;
	}

	public void setOrder_count(String order_count) {
		this.order_count = order_count;
	}

	public String getDebt_amount() {
		return debt_amount;
	}

	public void setDebt_amount(String debt_amount) {
		this.debt_amount = debt_amount;
	}

	public List<OverdueDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OverdueDetail> details) {
		this.details = details;
	}

}

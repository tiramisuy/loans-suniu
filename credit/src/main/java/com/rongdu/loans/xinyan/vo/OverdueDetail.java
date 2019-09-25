package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

public class OverdueDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 逾期时间,具体一笔逾期订单的逾期时间，按照YYYYMM形式输出
	private String date;
	// 逾期账期数，具体一笔逾期订单的逾期时⻓长，按【账期类型（S/M）+账龄(0/1/...)】形式输出，S代表7/14/21天等超短期现⾦金金贷账期、M代表30/30+天等多期借贷账期
	private String count;
	// 逾期⾦金金额，具体一笔逾期订单的逾期⾦金金额；单位:元
	private String amount;
	// 是否结清，具体一笔逾期订单的最终状态是否结清，Y表示已结清，N表示未结清
	private String settlement;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

}

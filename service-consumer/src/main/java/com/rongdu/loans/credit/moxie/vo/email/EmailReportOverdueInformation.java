package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 逾期信息
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportOverdueInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String total_overdue_bill_count;// 逾期账单数量
	private String total_overdue_amount;// 未还金额
	private String total_overdue_amount_div_new_balance;// 未还金额占比
	private String overdue_flag;// 逾期标志
	private String overdue_status;// 逾期状态

	public String getTotal_overdue_bill_count() {
		return total_overdue_bill_count;
	}

	public void setTotal_overdue_bill_count(String total_overdue_bill_count) {
		this.total_overdue_bill_count = total_overdue_bill_count;
	}

	public String getTotal_overdue_amount() {
		return total_overdue_amount;
	}

	public void setTotal_overdue_amount(String total_overdue_amount) {
		this.total_overdue_amount = total_overdue_amount;
	}

	public String getTotal_overdue_amount_div_new_balance() {
		return total_overdue_amount_div_new_balance;
	}

	public void setTotal_overdue_amount_div_new_balance(String total_overdue_amount_div_new_balance) {
		this.total_overdue_amount_div_new_balance = total_overdue_amount_div_new_balance;
	}

	public String getOverdue_flag() {
		return overdue_flag;
	}

	public void setOverdue_flag(String overdue_flag) {
		this.overdue_flag = overdue_flag;
	}

	public String getOverdue_status() {
		return overdue_status;
	}

	public void setOverdue_status(String overdue_status) {
		this.overdue_status = overdue_status;
	}

}

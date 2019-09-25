package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 利费信息
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportInterestFeeInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String total_interest_amount;// 总利息
	private String total_overdue_fine_amount;// 滞纳金
	private String total_excess_amount;// 超额金
	private String total_extra_amount;// 其他费用
	private String total_revenue_amount;// 收入 （利息+滞纳金+超额金+其他费用+分期手续费）
	private String total_installment_fee_amount;// 分期手续费

	public String getTotal_interest_amount() {
		return total_interest_amount;
	}

	public void setTotal_interest_amount(String total_interest_amount) {
		this.total_interest_amount = total_interest_amount;
	}

	public String getTotal_overdue_fine_amount() {
		return total_overdue_fine_amount;
	}

	public void setTotal_overdue_fine_amount(String total_overdue_fine_amount) {
		this.total_overdue_fine_amount = total_overdue_fine_amount;
	}

	public String getTotal_excess_amount() {
		return total_excess_amount;
	}

	public void setTotal_excess_amount(String total_excess_amount) {
		this.total_excess_amount = total_excess_amount;
	}

	public String getTotal_extra_amount() {
		return total_extra_amount;
	}

	public void setTotal_extra_amount(String total_extra_amount) {
		this.total_extra_amount = total_extra_amount;
	}

	public String getTotal_revenue_amount() {
		return total_revenue_amount;
	}

	public void setTotal_revenue_amount(String total_revenue_amount) {
		this.total_revenue_amount = total_revenue_amount;
	}

	public String getTotal_installment_fee_amount() {
		return total_installment_fee_amount;
	}

	public void setTotal_installment_fee_amount(String total_installment_fee_amount) {
		this.total_installment_fee_amount = total_installment_fee_amount;
	}

}

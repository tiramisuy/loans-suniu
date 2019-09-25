package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 还款
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportPaymentInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String total_payment_amount;// 还款金额
	private String total_payments_times;// 还款笔数
	private String repay_ratio;// 还款率

	public String getTotal_payment_amount() {
		return total_payment_amount;
	}

	public void setTotal_payment_amount(String total_payment_amount) {
		this.total_payment_amount = total_payment_amount;
	}

	public String getTotal_payments_times() {
		return total_payments_times;
	}

	public void setTotal_payments_times(String total_payments_times) {
		this.total_payments_times = total_payments_times;
	}

	public String getRepay_ratio() {
		return repay_ratio;
	}

	public void setRepay_ratio(String repay_ratio) {
		this.repay_ratio = repay_ratio;
	}

}

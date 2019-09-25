package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 还款
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportPaymentAnalyzeInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String average_payment_amount_l3m;// 近3个月均还款金额 34006.95
	private String average_payment_amount_l6m;// 近6个月均还款金额 29789.75
	private String average_payment_amount_l12m;// 近12个月均还款金额 49789.75

	private String average_payment_ratio_l3m;// 近3月均还款率 -1.00
	private String average_payment_ratio_l6m;// 近6月均还款率 -1.00
	private String average_payment_ratio_l12m;// 近12月均还款率 -1.00

	private String months_last_payment_from_now_l3m;// 近3月最近一次产生还款金额距今的月数 1
	private String months_last_payment_from_now_l6m;// 近6月最近一次产生还款金额距今的月数 1
	private String months_last_payment_from_now_l12m;// 近12月最近一次产生还款金额距今的月数 1

	private String months_min_payment_less_new_balance_l3m;// 近3月有MINPAY且不足全额的月份数
	private String months_min_payment_less_new_balance_l6m;// 近6月有MINPAY且不足全额的月份数
	private String months_min_payment_less_new_balance_l12m;// 近12月有MINPAY且不足全额的月份数

	public String getAverage_payment_amount_l3m() {
		return average_payment_amount_l3m;
	}

	public void setAverage_payment_amount_l3m(String average_payment_amount_l3m) {
		this.average_payment_amount_l3m = average_payment_amount_l3m;
	}

	public String getAverage_payment_ratio_l3m() {
		return average_payment_ratio_l3m;
	}

	public void setAverage_payment_ratio_l3m(String average_payment_ratio_l3m) {
		this.average_payment_ratio_l3m = average_payment_ratio_l3m;
	}

	public String getAverage_payment_amount_l6m() {
		return average_payment_amount_l6m;
	}

	public void setAverage_payment_amount_l6m(String average_payment_amount_l6m) {
		this.average_payment_amount_l6m = average_payment_amount_l6m;
	}

	public String getAverage_payment_ratio_l6m() {
		return average_payment_ratio_l6m;
	}

	public void setAverage_payment_ratio_l6m(String average_payment_ratio_l6m) {
		this.average_payment_ratio_l6m = average_payment_ratio_l6m;
	}

	public String getAverage_payment_ratio_l12m() {
		return average_payment_ratio_l12m;
	}

	public void setAverage_payment_ratio_l12m(String average_payment_ratio_l12m) {
		this.average_payment_ratio_l12m = average_payment_ratio_l12m;
	}

	public String getAverage_payment_amount_l12m() {
		return average_payment_amount_l12m;
	}

	public void setAverage_payment_amount_l12m(String average_payment_amount_l12m) {
		this.average_payment_amount_l12m = average_payment_amount_l12m;
	}

	public String getMonths_min_payment_less_new_balance_l3m() {
		return months_min_payment_less_new_balance_l3m;
	}

	public void setMonths_min_payment_less_new_balance_l3m(String months_min_payment_less_new_balance_l3m) {
		this.months_min_payment_less_new_balance_l3m = months_min_payment_less_new_balance_l3m;
	}

	public String getMonths_last_payment_from_now_l3m() {
		return months_last_payment_from_now_l3m;
	}

	public void setMonths_last_payment_from_now_l3m(String months_last_payment_from_now_l3m) {
		this.months_last_payment_from_now_l3m = months_last_payment_from_now_l3m;
	}

	public String getMonths_last_payment_from_now_l6m() {
		return months_last_payment_from_now_l6m;
	}

	public void setMonths_last_payment_from_now_l6m(String months_last_payment_from_now_l6m) {
		this.months_last_payment_from_now_l6m = months_last_payment_from_now_l6m;
	}

	public String getMonths_last_payment_from_now_l12m() {
		return months_last_payment_from_now_l12m;
	}

	public void setMonths_last_payment_from_now_l12m(String months_last_payment_from_now_l12m) {
		this.months_last_payment_from_now_l12m = months_last_payment_from_now_l12m;
	}

	public String getMonths_min_payment_less_new_balance_l6m() {
		return months_min_payment_less_new_balance_l6m;
	}

	public void setMonths_min_payment_less_new_balance_l6m(String months_min_payment_less_new_balance_l6m) {
		this.months_min_payment_less_new_balance_l6m = months_min_payment_less_new_balance_l6m;
	}

	public String getMonths_min_payment_less_new_balance_l12m() {
		return months_min_payment_less_new_balance_l12m;
	}

	public void setMonths_min_payment_less_new_balance_l12m(String months_min_payment_less_new_balance_l12m) {
		this.months_min_payment_less_new_balance_l12m = months_min_payment_less_new_balance_l12m;
	}

}

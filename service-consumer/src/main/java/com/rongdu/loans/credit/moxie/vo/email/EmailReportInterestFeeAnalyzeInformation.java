package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 利息
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportInterestFeeAnalyzeInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String months_with_interest_l3m;// 近3月有利息的月份数 1
	private String months_with_interest_l6m;// 近6月有利息的月份数 1
	private String months_with_interest_l12m;// 近12月有利息的月份数 1

	private String interest_months_ratio_l6m;// 近6月有利息的月数占比 0.33
	private String interest_months_ratio_l12m;// 近12月有利息的月数占比 0.17
	private String interest_months_ratio_l3m;// 近3月有利息的月数占比 0.08

	public String getMonths_with_interest_l3m() {
		return months_with_interest_l3m;
	}

	public void setMonths_with_interest_l3m(String months_with_interest_l3m) {
		this.months_with_interest_l3m = months_with_interest_l3m;
	}

	public String getMonths_with_interest_l6m() {
		return months_with_interest_l6m;
	}

	public void setMonths_with_interest_l6m(String months_with_interest_l6m) {
		this.months_with_interest_l6m = months_with_interest_l6m;
	}

	public String getMonths_with_interest_l12m() {
		return months_with_interest_l12m;
	}

	public void setMonths_with_interest_l12m(String months_with_interest_l12m) {
		this.months_with_interest_l12m = months_with_interest_l12m;
	}

	public String getInterest_months_ratio_l6m() {
		return interest_months_ratio_l6m;
	}

	public void setInterest_months_ratio_l6m(String interest_months_ratio_l6m) {
		this.interest_months_ratio_l6m = interest_months_ratio_l6m;
	}

	public String getInterest_months_ratio_l12m() {
		return interest_months_ratio_l12m;
	}

	public void setInterest_months_ratio_l12m(String interest_months_ratio_l12m) {
		this.interest_months_ratio_l12m = interest_months_ratio_l12m;
	}

	public String getInterest_months_ratio_l3m() {
		return interest_months_ratio_l3m;
	}

	public void setInterest_months_ratio_l3m(String interest_months_ratio_l3m) {
		this.interest_months_ratio_l3m = interest_months_ratio_l3m;
	}

}

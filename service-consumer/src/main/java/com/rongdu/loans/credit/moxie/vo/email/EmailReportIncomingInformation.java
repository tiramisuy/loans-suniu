package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 收入
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportIncomingInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String average_income_l3m;// 近3月平均收入 5487.28
	private String average_income_l6m;// 近6月平均收入 3380.23
	private String average_income_l12m;// 近12月平均收入 2299.38

	private String months_min_income_from_now_l3m;// 近3月最低收入距今月份数 2
	private String months_min_income_from_now_l6m;// 近6月最低收入距今月份数 6
	private String months_min_income_from_now_l12m;// 近12月最低收入距今月份数 6
	
	private String average_income_l3m_div_average_income_l6m;// 近3月平均收入与近6期平均比
	private String ratio_l6m_average_income_div_l12M_average_income;// 近6月平均收入与近12期平均比

	public String getAverage_income_l3m_div_average_income_l6m() {
		return average_income_l3m_div_average_income_l6m;
	}

	public void setAverage_income_l3m_div_average_income_l6m(String average_income_l3m_div_average_income_l6m) {
		this.average_income_l3m_div_average_income_l6m = average_income_l3m_div_average_income_l6m;
	}

	public String getAverage_income_l3m() {
		return average_income_l3m;
	}

	public void setAverage_income_l3m(String average_income_l3m) {
		this.average_income_l3m = average_income_l3m;
	}

	public String getAverage_income_l6m() {
		return average_income_l6m;
	}

	public void setAverage_income_l6m(String average_income_l6m) {
		this.average_income_l6m = average_income_l6m;
	}

	public String getAverage_income_l12m() {
		return average_income_l12m;
	}

	public void setAverage_income_l12m(String average_income_l12m) {
		this.average_income_l12m = average_income_l12m;
	}

	public String getRatio_l6m_average_income_div_l12M_average_income() {
		return ratio_l6m_average_income_div_l12M_average_income;
	}

	public void setRatio_l6m_average_income_div_l12M_average_income(
			String ratio_l6m_average_income_div_l12M_average_income) {
		this.ratio_l6m_average_income_div_l12M_average_income = ratio_l6m_average_income_div_l12M_average_income;
	}

	public String getMonths_min_income_from_now_l3m() {
		return months_min_income_from_now_l3m;
	}

	public void setMonths_min_income_from_now_l3m(String months_min_income_from_now_l3m) {
		this.months_min_income_from_now_l3m = months_min_income_from_now_l3m;
	}

	public String getMonths_min_income_from_now_l6m() {
		return months_min_income_from_now_l6m;
	}

	public void setMonths_min_income_from_now_l6m(String months_min_income_from_now_l6m) {
		this.months_min_income_from_now_l6m = months_min_income_from_now_l6m;
	}

	public String getMonths_min_income_from_now_l12m() {
		return months_min_income_from_now_l12m;
	}

	public void setMonths_min_income_from_now_l12m(String months_min_income_from_now_l12m) {
		this.months_min_income_from_now_l12m = months_min_income_from_now_l12m;
	}

}

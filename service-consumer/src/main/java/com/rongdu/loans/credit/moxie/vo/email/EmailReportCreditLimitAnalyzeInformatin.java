package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 额度
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportCreditLimitAnalyzeInformatin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String total_credit_limit_l3m;// 近3月可用额度 30464.24
	private String total_credit_limit_l6m;// 近6月可用额度 26408.93
	private String total_credit_limit_l12m;// 近12月可用额度 36408.93

	private String new_balance_div_credit_limit_l3m;// 近3月均余额占额度比例 1.42
	private String average_new_balance_div_credit_limit_l6m;// 近6月均余额占额度比例 1.12
	private String average_new_balance_div_credit_limit_l12m;// 近12月均余额占额度比例

	private String new_charge_div_credit_limit_l3m;// 近3月销售金额占额度比 3.30
	private String sell_div_credit_limit_l6m;// 近6月销售金额占额度比 6.40
	private String sell_div_credit_limit_l12m;// 近12月销售金额占额度比 8.00

	private String max_sell_div_credit_limit_l3m;// 近3月中最大销售金额占额度比 1.65
	private String max_sell_div_credit_limit_l6m;// 近6月中最大销售金额占额度比 1.92
	private String max_sell_div_credit_limit_l12m;// 近12月中最大销售金额占额度比 1.92

	private String min_sell_div_credit_limit_l3m;// 近3月中最小销售金额占额度比 0.08
	private String min_sell_div_credit_limit_l6m;// 近6月中最小销售金额占额度比 0.08
	private String min_sell_div_credit_limit_12m;// 近12月中最小销售金额占额度比 0.08

	private String average_credit_limit_l3m;// 近3月平均额度 23900.00
	private String average_credit_limit_l6m;// 近6月平均额度 23900.00
	private String average_credit_limit_l12m;// 近12月平均额度 23900.00

	private String max_new_balance_div_credit_limit_l3m;// 近3月最大额度使用率 100
	private String max_new_balance_div_credit_limit_l6m;// 近6月最大额度使用率 100
	private String max_new_balance_div_credit_limit_l12m;// 近12月最大额度使用率 100

	private String max_credit_limit_l3m;// 近3月最高额度 23900.00
	private String max_credit_limit_l6m;// 近6月最高额度 23900.00
	private String max_credit_limit_l12m;// 近12月最高额度 23900.00

	private String min_credit_limit_l3m;// 近3月最低额度 23900.00
	private String min_credit_limit_l6m;// 近6月最低额度 23900.00
	private String min_credit_limit_l12m;// 近12月最低额度 23900.00

	private String months_sell_div_credit_limit_above_half_l3m;// 近3月中销售金额占额度比大于0.5的月数
	private String months_new_sell_div_credit_limit_above_half_l6m;// 近6月中销售金额占额度比大于0.5的月数
	private String months_new_sell_div_credit_limit_above_half_l12m;// 近12月中销售金额占额度比大于0.5的月数

	private String monhts_new_balance_div_credit_limit_above_90pct_l3m;// 近3月中销售金额占额度比大于0.9的月数
	private String months_new_sell_div_credit_limit_above_90pct_l6m;// 近6月中销售金额占额度比大于0.9的月数
	private String months_new_sell_div_credit_limit_above_90pct_l12m;// 近12月中销售金额占额度比大于0.9的月数

	private String max_ratio_latest_month_usage_ratio_div_l6m;// 最近一月额度使用率与近6月最大比
	private String max_ratio_latest_month_usage_ratio_div_l3m;// 最近一月额度使用率与近3期最大比
	private String max_ratio_latest_month_usage_ratio_div_l12m;// 最近一月额度使用率与近12期最大比

	private String average_sell_div_credit_limit_l3m;// 近3月平均销售金额／信用额度 1.10
	private String average_sell_div_credit_limit_l6m;// 近6月均销售金额/信用额度 1.07
	private String average_sell_div_credit_limit_l12m;// 近12月均销售金额/信用额度 0.67

	private String min_credit_limit_usage_ratio_l3m;// 近3月最小额度使用率 47.0
	private String min_credit_limit_usage_ratio_l6m;// 近6月最小额度使用率 33.0
	private String min_credit_limit_usage_ratio_l12m;// 近12月最小额度使用率 33.0

	private String average_credit_limit_usage_ratio_l3m;// 近3月均额度使用率 82.33
	private String average_credit_limit_usage_ratio_l6m;// 近6月均额度使用率 75.83
	private String average_credit_limit_usage_ratio_l12m;// 近12月均额度使用率 63.17

	private String latest_ratio_above_zero_l3m;// 近3月最近一次额度使用率>0的值 47.0
	private String latest_ratio_above_zero_l6m;// 近6月最近一次额度使用率>0的值 47.0
	private String latest_ratio_above_zero_l12m;// 近12月最近一次额度使用率>0的值 47.0

	public String getTotal_credit_limit_l3m() {
		return total_credit_limit_l3m;
	}

	public void setTotal_credit_limit_l3m(String total_credit_limit_l3m) {
		this.total_credit_limit_l3m = total_credit_limit_l3m;
	}

	public String getNew_balance_div_credit_limit_l3m() {
		return new_balance_div_credit_limit_l3m;
	}

	public void setNew_balance_div_credit_limit_l3m(String new_balance_div_credit_limit_l3m) {
		this.new_balance_div_credit_limit_l3m = new_balance_div_credit_limit_l3m;
	}

	public String getNew_charge_div_credit_limit_l3m() {
		return new_charge_div_credit_limit_l3m;
	}

	public void setNew_charge_div_credit_limit_l3m(String new_charge_div_credit_limit_l3m) {
		this.new_charge_div_credit_limit_l3m = new_charge_div_credit_limit_l3m;
	}

	public String getMax_sell_div_credit_limit_l3m() {
		return max_sell_div_credit_limit_l3m;
	}

	public void setMax_sell_div_credit_limit_l3m(String max_sell_div_credit_limit_l3m) {
		this.max_sell_div_credit_limit_l3m = max_sell_div_credit_limit_l3m;
	}

	public String getMin_sell_div_credit_limit_l3m() {
		return min_sell_div_credit_limit_l3m;
	}

	public void setMin_sell_div_credit_limit_l3m(String min_sell_div_credit_limit_l3m) {
		this.min_sell_div_credit_limit_l3m = min_sell_div_credit_limit_l3m;
	}

	public String getAverage_credit_limit_l3m() {
		return average_credit_limit_l3m;
	}

	public void setAverage_credit_limit_l3m(String average_credit_limit_l3m) {
		this.average_credit_limit_l3m = average_credit_limit_l3m;
	}

	public String getMax_new_balance_div_credit_limit_l3m() {
		return max_new_balance_div_credit_limit_l3m;
	}

	public void setMax_new_balance_div_credit_limit_l3m(String max_new_balance_div_credit_limit_l3m) {
		this.max_new_balance_div_credit_limit_l3m = max_new_balance_div_credit_limit_l3m;
	}

	public String getMax_credit_limit_l3m() {
		return max_credit_limit_l3m;
	}

	public void setMax_credit_limit_l3m(String max_credit_limit_l3m) {
		this.max_credit_limit_l3m = max_credit_limit_l3m;
	}

	public String getMin_credit_limit_l3m() {
		return min_credit_limit_l3m;
	}

	public void setMin_credit_limit_l3m(String min_credit_limit_l3m) {
		this.min_credit_limit_l3m = min_credit_limit_l3m;
	}

	public String getAverage_new_balance_div_credit_limit_l6m() {
		return average_new_balance_div_credit_limit_l6m;
	}

	public void setAverage_new_balance_div_credit_limit_l6m(String average_new_balance_div_credit_limit_l6m) {
		this.average_new_balance_div_credit_limit_l6m = average_new_balance_div_credit_limit_l6m;
	}

	public String getAverage_new_balance_div_credit_limit_l12m() {
		return average_new_balance_div_credit_limit_l12m;
	}

	public void setAverage_new_balance_div_credit_limit_l12m(String average_new_balance_div_credit_limit_l12m) {
		this.average_new_balance_div_credit_limit_l12m = average_new_balance_div_credit_limit_l12m;
	}

	public String getTotal_credit_limit_l6m() {
		return total_credit_limit_l6m;
	}

	public void setTotal_credit_limit_l6m(String total_credit_limit_l6m) {
		this.total_credit_limit_l6m = total_credit_limit_l6m;
	}

	public String getTotal_credit_limit_l12m() {
		return total_credit_limit_l12m;
	}

	public void setTotal_credit_limit_l12m(String total_credit_limit_l12m) {
		this.total_credit_limit_l12m = total_credit_limit_l12m;
	}

	public String getAverage_credit_limit_l6m() {
		return average_credit_limit_l6m;
	}

	public void setAverage_credit_limit_l6m(String average_credit_limit_l6m) {
		this.average_credit_limit_l6m = average_credit_limit_l6m;
	}

	public String getAverage_credit_limit_l12m() {
		return average_credit_limit_l12m;
	}

	public void setAverage_credit_limit_l12m(String average_credit_limit_l12m) {
		this.average_credit_limit_l12m = average_credit_limit_l12m;
	}

	public String getSell_div_credit_limit_l6m() {
		return sell_div_credit_limit_l6m;
	}

	public void setSell_div_credit_limit_l6m(String sell_div_credit_limit_l6m) {
		this.sell_div_credit_limit_l6m = sell_div_credit_limit_l6m;
	}

	public String getSell_div_credit_limit_l12m() {
		return sell_div_credit_limit_l12m;
	}

	public void setSell_div_credit_limit_l12m(String sell_div_credit_limit_l12m) {
		this.sell_div_credit_limit_l12m = sell_div_credit_limit_l12m;
	}

	public String getMax_credit_limit_l6m() {
		return max_credit_limit_l6m;
	}

	public void setMax_credit_limit_l6m(String max_credit_limit_l6m) {
		this.max_credit_limit_l6m = max_credit_limit_l6m;
	}

	public String getMax_credit_limit_l12m() {
		return max_credit_limit_l12m;
	}

	public void setMax_credit_limit_l12m(String max_credit_limit_l12m) {
		this.max_credit_limit_l12m = max_credit_limit_l12m;
	}

	public String getMin_credit_limit_l6m() {
		return min_credit_limit_l6m;
	}

	public void setMin_credit_limit_l6m(String min_credit_limit_l6m) {
		this.min_credit_limit_l6m = min_credit_limit_l6m;
	}

	public String getMin_credit_limit_l12m() {
		return min_credit_limit_l12m;
	}

	public void setMin_credit_limit_l12m(String min_credit_limit_l12m) {
		this.min_credit_limit_l12m = min_credit_limit_l12m;
	}

	public String getMax_sell_div_credit_limit_l6m() {
		return max_sell_div_credit_limit_l6m;
	}

	public void setMax_sell_div_credit_limit_l6m(String max_sell_div_credit_limit_l6m) {
		this.max_sell_div_credit_limit_l6m = max_sell_div_credit_limit_l6m;
	}

	public String getMax_sell_div_credit_limit_l12m() {
		return max_sell_div_credit_limit_l12m;
	}

	public void setMax_sell_div_credit_limit_l12m(String max_sell_div_credit_limit_l12m) {
		this.max_sell_div_credit_limit_l12m = max_sell_div_credit_limit_l12m;
	}

	public String getMin_sell_div_credit_limit_l6m() {
		return min_sell_div_credit_limit_l6m;
	}

	public void setMin_sell_div_credit_limit_l6m(String min_sell_div_credit_limit_l6m) {
		this.min_sell_div_credit_limit_l6m = min_sell_div_credit_limit_l6m;
	}

	public String getMin_sell_div_credit_limit_12m() {
		return min_sell_div_credit_limit_12m;
	}

	public void setMin_sell_div_credit_limit_12m(String min_sell_div_credit_limit_12m) {
		this.min_sell_div_credit_limit_12m = min_sell_div_credit_limit_12m;
	}

	public String getMax_new_balance_div_credit_limit_l6m() {
		return max_new_balance_div_credit_limit_l6m;
	}

	public void setMax_new_balance_div_credit_limit_l6m(String max_new_balance_div_credit_limit_l6m) {
		this.max_new_balance_div_credit_limit_l6m = max_new_balance_div_credit_limit_l6m;
	}

	public String getMax_new_balance_div_credit_limit_l12m() {
		return max_new_balance_div_credit_limit_l12m;
	}

	public void setMax_new_balance_div_credit_limit_l12m(String max_new_balance_div_credit_limit_l12m) {
		this.max_new_balance_div_credit_limit_l12m = max_new_balance_div_credit_limit_l12m;
	}

	public String getMonths_sell_div_credit_limit_above_half_l3m() {
		return months_sell_div_credit_limit_above_half_l3m;
	}

	public void setMonths_sell_div_credit_limit_above_half_l3m(String months_sell_div_credit_limit_above_half_l3m) {
		this.months_sell_div_credit_limit_above_half_l3m = months_sell_div_credit_limit_above_half_l3m;
	}

	public String getMax_ratio_latest_month_usage_ratio_div_l6m() {
		return max_ratio_latest_month_usage_ratio_div_l6m;
	}

	public void setMax_ratio_latest_month_usage_ratio_div_l6m(String max_ratio_latest_month_usage_ratio_div_l6m) {
		this.max_ratio_latest_month_usage_ratio_div_l6m = max_ratio_latest_month_usage_ratio_div_l6m;
	}

	public String getMax_ratio_latest_month_usage_ratio_div_l3m() {
		return max_ratio_latest_month_usage_ratio_div_l3m;
	}

	public void setMax_ratio_latest_month_usage_ratio_div_l3m(String max_ratio_latest_month_usage_ratio_div_l3m) {
		this.max_ratio_latest_month_usage_ratio_div_l3m = max_ratio_latest_month_usage_ratio_div_l3m;
	}

	public String getAverage_sell_div_credit_limit_l3m() {
		return average_sell_div_credit_limit_l3m;
	}

	public void setAverage_sell_div_credit_limit_l3m(String average_sell_div_credit_limit_l3m) {
		this.average_sell_div_credit_limit_l3m = average_sell_div_credit_limit_l3m;
	}

	public String getMax_ratio_latest_month_usage_ratio_div_l12m() {
		return max_ratio_latest_month_usage_ratio_div_l12m;
	}

	public void setMax_ratio_latest_month_usage_ratio_div_l12m(String max_ratio_latest_month_usage_ratio_div_l12m) {
		this.max_ratio_latest_month_usage_ratio_div_l12m = max_ratio_latest_month_usage_ratio_div_l12m;
	}

	public String getMin_credit_limit_usage_ratio_l3m() {
		return min_credit_limit_usage_ratio_l3m;
	}

	public void setMin_credit_limit_usage_ratio_l3m(String min_credit_limit_usage_ratio_l3m) {
		this.min_credit_limit_usage_ratio_l3m = min_credit_limit_usage_ratio_l3m;
	}

	public String getMin_credit_limit_usage_ratio_l6m() {
		return min_credit_limit_usage_ratio_l6m;
	}

	public void setMin_credit_limit_usage_ratio_l6m(String min_credit_limit_usage_ratio_l6m) {
		this.min_credit_limit_usage_ratio_l6m = min_credit_limit_usage_ratio_l6m;
	}

	public String getMin_credit_limit_usage_ratio_l12m() {
		return min_credit_limit_usage_ratio_l12m;
	}

	public void setMin_credit_limit_usage_ratio_l12m(String min_credit_limit_usage_ratio_l12m) {
		this.min_credit_limit_usage_ratio_l12m = min_credit_limit_usage_ratio_l12m;
	}

	public String getMonths_new_sell_div_credit_limit_above_half_l6m() {
		return months_new_sell_div_credit_limit_above_half_l6m;
	}

	public void setMonths_new_sell_div_credit_limit_above_half_l6m(
			String months_new_sell_div_credit_limit_above_half_l6m) {
		this.months_new_sell_div_credit_limit_above_half_l6m = months_new_sell_div_credit_limit_above_half_l6m;
	}

	public String getMonths_new_sell_div_credit_limit_above_half_l12m() {
		return months_new_sell_div_credit_limit_above_half_l12m;
	}

	public void setMonths_new_sell_div_credit_limit_above_half_l12m(
			String months_new_sell_div_credit_limit_above_half_l12m) {
		this.months_new_sell_div_credit_limit_above_half_l12m = months_new_sell_div_credit_limit_above_half_l12m;
	}

	public String getAverage_credit_limit_usage_ratio_l6m() {
		return average_credit_limit_usage_ratio_l6m;
	}

	public void setAverage_credit_limit_usage_ratio_l6m(String average_credit_limit_usage_ratio_l6m) {
		this.average_credit_limit_usage_ratio_l6m = average_credit_limit_usage_ratio_l6m;
	}

	public String getMonhts_new_balance_div_credit_limit_above_90pct_l3m() {
		return monhts_new_balance_div_credit_limit_above_90pct_l3m;
	}

	public void setMonhts_new_balance_div_credit_limit_above_90pct_l3m(
			String monhts_new_balance_div_credit_limit_above_90pct_l3m) {
		this.monhts_new_balance_div_credit_limit_above_90pct_l3m = monhts_new_balance_div_credit_limit_above_90pct_l3m;
	}

	public String getAverage_credit_limit_usage_ratio_l12m() {
		return average_credit_limit_usage_ratio_l12m;
	}

	public void setAverage_credit_limit_usage_ratio_l12m(String average_credit_limit_usage_ratio_l12m) {
		this.average_credit_limit_usage_ratio_l12m = average_credit_limit_usage_ratio_l12m;
	}

	public String getAverage_credit_limit_usage_ratio_l3m() {
		return average_credit_limit_usage_ratio_l3m;
	}

	public void setAverage_credit_limit_usage_ratio_l3m(String average_credit_limit_usage_ratio_l3m) {
		this.average_credit_limit_usage_ratio_l3m = average_credit_limit_usage_ratio_l3m;
	}

	public String getLatest_ratio_above_zero_l3m() {
		return latest_ratio_above_zero_l3m;
	}

	public void setLatest_ratio_above_zero_l3m(String latest_ratio_above_zero_l3m) {
		this.latest_ratio_above_zero_l3m = latest_ratio_above_zero_l3m;
	}

	public String getMonths_new_sell_div_credit_limit_above_90pct_l6m() {
		return months_new_sell_div_credit_limit_above_90pct_l6m;
	}

	public void setMonths_new_sell_div_credit_limit_above_90pct_l6m(
			String months_new_sell_div_credit_limit_above_90pct_l6m) {
		this.months_new_sell_div_credit_limit_above_90pct_l6m = months_new_sell_div_credit_limit_above_90pct_l6m;
	}

	public String getMonths_new_sell_div_credit_limit_above_90pct_l12m() {
		return months_new_sell_div_credit_limit_above_90pct_l12m;
	}

	public void setMonths_new_sell_div_credit_limit_above_90pct_l12m(
			String months_new_sell_div_credit_limit_above_90pct_l12m) {
		this.months_new_sell_div_credit_limit_above_90pct_l12m = months_new_sell_div_credit_limit_above_90pct_l12m;
	}

	public String getAverage_sell_div_credit_limit_l6m() {
		return average_sell_div_credit_limit_l6m;
	}

	public void setAverage_sell_div_credit_limit_l6m(String average_sell_div_credit_limit_l6m) {
		this.average_sell_div_credit_limit_l6m = average_sell_div_credit_limit_l6m;
	}

	public String getAverage_sell_div_credit_limit_l12m() {
		return average_sell_div_credit_limit_l12m;
	}

	public void setAverage_sell_div_credit_limit_l12m(String average_sell_div_credit_limit_l12m) {
		this.average_sell_div_credit_limit_l12m = average_sell_div_credit_limit_l12m;
	}

	public String getLatest_ratio_above_zero_l6m() {
		return latest_ratio_above_zero_l6m;
	}

	public void setLatest_ratio_above_zero_l6m(String latest_ratio_above_zero_l6m) {
		this.latest_ratio_above_zero_l6m = latest_ratio_above_zero_l6m;
	}

	public String getLatest_ratio_above_zero_l12m() {
		return latest_ratio_above_zero_l12m;
	}

	public void setLatest_ratio_above_zero_l12m(String latest_ratio_above_zero_l12m) {
		this.latest_ratio_above_zero_l12m = latest_ratio_above_zero_l12m;
	}

}

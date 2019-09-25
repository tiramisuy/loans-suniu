package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 销售金额
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportNewChargeInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ratio_sell_l3m;// 近3月销售金额大于0的月份占比 1.00
	private String ratio_sell_l6m;// 近6月销售金额大于0的月份占比 1.00
	private String ratio_sell_l12m;// 近12月销售金额大于0的月份占比 1.00

	private String average_consume_l3m;// 近3月月均消费金额 15197.05
	private String average_consume_l6m;// 近6月月均消费金额 15117.05
	private String average_consume_l12m;// 近12月月均消费金额 12397.05

	private String max_consume_l3m;// 近3月最高消费金额 7299.00
	private String max_consume_l6m;// 近6月最高消费金额 7299.00
	private String max_consume_l12m;// 近12月最高消费金额 7299.00

	private String min_consume_l3m;// 近3月最低消费金额 6.00
	private String min_consume_l6m;// 近6月最低消费金额 6.00
	private String min_consume_l12m;// 近12月最低消费金额 6.00

	private String average_withdraw_amount_l3m;// 近3月月均提现金额 6689.00
	private String average_withdraw_amount_l6m;// 近6月月均提现金额 2229.67
	private String average_withdraw_amount_l12m;// 近12月月均提现金额 4219.67

	private String months_from_last_withdraw_l3m;// 近3月最近一次提现距今月数 2
	private String months_from_last_withdraw_l6m;// 近6月最近一次提现距今月数 2
	private String months_from_last_withdraw_l12m;// 近12月最近一次提现距今月数 2

	private String first_sell_amount_l3m;// 近3月首次销售金额 750.00
	private String first_sell_amount_l6m;// 近6月首次销售金额 1200.00
	private String first_sell_amount_l12m;// 近12月首次销售金额 1314.00

	private String first_consume_amount_l3m;// 近3月首次消费金额 750.00
	private String first_consume_amount_l6m;// 近6月首次消费金额 1200.00
	private String first_consume_amount_l12m;// 近12月首次消费金额 1314.00

	private String max_sell_count_l3m;// 近3月单期最大销售笔数 20
	private String max_sell_count_l6m;// 近6月单期最大销售笔数 20
	private String max_sell_count_l12m;// 近12月单期最大销售笔数 30

	private String months_have_sell_l3m;// 近3月有销售的月数 3
	private String months_have_sell_l6m;// 近6月有销售的月数 6
	private String months_have_sell_l12m;// 近12月有销售的月数 12

	private String average_sell_count_l3m;// 近3月月均消费笔数 16
	private String average_sell_count_l6m;// 近6月月均消费笔数 13
	private String average_sell_count_l12m;// 近12月月均消费笔数 15

	private String ratio_withdraw_months_div_all_months_l3m;// 近3月有提现月数占比 0.67
	private String ratio_withdraw_months_div_all_months_l6m;// 近6月有提现月数占比 0.83
	private String ratio_withdraw_months_div_all_months_l12m;// 近12月有提现月数占比 0.83

	private String times_withdraw_deposit_l3m;// 近3月提现次数 3
	private String total_withdraw_count_l6m;// 近6月提现次数 6
	private String total_withdraw_count_l12m;// 近12月提现次数 12

	private String withdraw_amount_l3m;// 近3月提现金额 6689.00
	private String withdraw_amount_l6m;// 近6月提现金额 18080.12
	private String withdraw_amount_l12m;// 近12月提现金额 36410.12

	public String getRatio_sell_l3m() {
		return ratio_sell_l3m;
	}

	public void setRatio_sell_l3m(String ratio_sell_l3m) {
		this.ratio_sell_l3m = ratio_sell_l3m;
	}

	public String getRatio_sell_l6m() {
		return ratio_sell_l6m;
	}

	public void setRatio_sell_l6m(String ratio_sell_l6m) {
		this.ratio_sell_l6m = ratio_sell_l6m;
	}

	public String getRatio_sell_l12m() {
		return ratio_sell_l12m;
	}

	public void setRatio_sell_l12m(String ratio_sell_l12m) {
		this.ratio_sell_l12m = ratio_sell_l12m;
	}

	public String getAverage_consume_l3m() {
		return average_consume_l3m;
	}

	public void setAverage_consume_l3m(String average_consume_l3m) {
		this.average_consume_l3m = average_consume_l3m;
	}

	public String getAverage_consume_l6m() {
		return average_consume_l6m;
	}

	public void setAverage_consume_l6m(String average_consume_l6m) {
		this.average_consume_l6m = average_consume_l6m;
	}

	public String getAverage_consume_l12m() {
		return average_consume_l12m;
	}

	public void setAverage_consume_l12m(String average_consume_l12m) {
		this.average_consume_l12m = average_consume_l12m;
	}

	public String getMax_consume_l3m() {
		return max_consume_l3m;
	}

	public void setMax_consume_l3m(String max_consume_l3m) {
		this.max_consume_l3m = max_consume_l3m;
	}

	public String getMax_consume_l6m() {
		return max_consume_l6m;
	}

	public void setMax_consume_l6m(String max_consume_l6m) {
		this.max_consume_l6m = max_consume_l6m;
	}

	public String getMax_consume_l12m() {
		return max_consume_l12m;
	}

	public void setMax_consume_l12m(String max_consume_l12m) {
		this.max_consume_l12m = max_consume_l12m;
	}

	public String getMin_consume_l3m() {
		return min_consume_l3m;
	}

	public void setMin_consume_l3m(String min_consume_l3m) {
		this.min_consume_l3m = min_consume_l3m;
	}

	public String getMin_consume_l6m() {
		return min_consume_l6m;
	}

	public void setMin_consume_l6m(String min_consume_l6m) {
		this.min_consume_l6m = min_consume_l6m;
	}

	public String getMin_consume_l12m() {
		return min_consume_l12m;
	}

	public void setMin_consume_l12m(String min_consume_l12m) {
		this.min_consume_l12m = min_consume_l12m;
	}

	public String getAverage_withdraw_amount_l3m() {
		return average_withdraw_amount_l3m;
	}

	public void setAverage_withdraw_amount_l3m(String average_withdraw_amount_l3m) {
		this.average_withdraw_amount_l3m = average_withdraw_amount_l3m;
	}

	public String getAverage_withdraw_amount_l6m() {
		return average_withdraw_amount_l6m;
	}

	public void setAverage_withdraw_amount_l6m(String average_withdraw_amount_l6m) {
		this.average_withdraw_amount_l6m = average_withdraw_amount_l6m;
	}

	public String getAverage_withdraw_amount_l12m() {
		return average_withdraw_amount_l12m;
	}

	public void setAverage_withdraw_amount_l12m(String average_withdraw_amount_l12m) {
		this.average_withdraw_amount_l12m = average_withdraw_amount_l12m;
	}

	public String getMonths_from_last_withdraw_l3m() {
		return months_from_last_withdraw_l3m;
	}

	public void setMonths_from_last_withdraw_l3m(String months_from_last_withdraw_l3m) {
		this.months_from_last_withdraw_l3m = months_from_last_withdraw_l3m;
	}

	public String getMonths_from_last_withdraw_l6m() {
		return months_from_last_withdraw_l6m;
	}

	public void setMonths_from_last_withdraw_l6m(String months_from_last_withdraw_l6m) {
		this.months_from_last_withdraw_l6m = months_from_last_withdraw_l6m;
	}

	public String getMonths_from_last_withdraw_l12m() {
		return months_from_last_withdraw_l12m;
	}

	public void setMonths_from_last_withdraw_l12m(String months_from_last_withdraw_l12m) {
		this.months_from_last_withdraw_l12m = months_from_last_withdraw_l12m;
	}

	public String getFirst_sell_amount_l3m() {
		return first_sell_amount_l3m;
	}

	public void setFirst_sell_amount_l3m(String first_sell_amount_l3m) {
		this.first_sell_amount_l3m = first_sell_amount_l3m;
	}

	public String getFirst_sell_amount_l6m() {
		return first_sell_amount_l6m;
	}

	public void setFirst_sell_amount_l6m(String first_sell_amount_l6m) {
		this.first_sell_amount_l6m = first_sell_amount_l6m;
	}

	public String getFirst_sell_amount_l12m() {
		return first_sell_amount_l12m;
	}

	public void setFirst_sell_amount_l12m(String first_sell_amount_l12m) {
		this.first_sell_amount_l12m = first_sell_amount_l12m;
	}

	public String getFirst_consume_amount_l3m() {
		return first_consume_amount_l3m;
	}

	public void setFirst_consume_amount_l3m(String first_consume_amount_l3m) {
		this.first_consume_amount_l3m = first_consume_amount_l3m;
	}

	public String getFirst_consume_amount_l6m() {
		return first_consume_amount_l6m;
	}

	public void setFirst_consume_amount_l6m(String first_consume_amount_l6m) {
		this.first_consume_amount_l6m = first_consume_amount_l6m;
	}

	public String getFirst_consume_amount_l12m() {
		return first_consume_amount_l12m;
	}

	public void setFirst_consume_amount_l12m(String first_consume_amount_l12m) {
		this.first_consume_amount_l12m = first_consume_amount_l12m;
	}

	public String getMax_sell_count_l3m() {
		return max_sell_count_l3m;
	}

	public void setMax_sell_count_l3m(String max_sell_count_l3m) {
		this.max_sell_count_l3m = max_sell_count_l3m;
	}

	public String getMax_sell_count_l6m() {
		return max_sell_count_l6m;
	}

	public void setMax_sell_count_l6m(String max_sell_count_l6m) {
		this.max_sell_count_l6m = max_sell_count_l6m;
	}

	public String getMax_sell_count_l12m() {
		return max_sell_count_l12m;
	}

	public void setMax_sell_count_l12m(String max_sell_count_l12m) {
		this.max_sell_count_l12m = max_sell_count_l12m;
	}

	public String getMonths_have_sell_l3m() {
		return months_have_sell_l3m;
	}

	public void setMonths_have_sell_l3m(String months_have_sell_l3m) {
		this.months_have_sell_l3m = months_have_sell_l3m;
	}

	public String getMonths_have_sell_l6m() {
		return months_have_sell_l6m;
	}

	public void setMonths_have_sell_l6m(String months_have_sell_l6m) {
		this.months_have_sell_l6m = months_have_sell_l6m;
	}

	public String getMonths_have_sell_l12m() {
		return months_have_sell_l12m;
	}

	public void setMonths_have_sell_l12m(String months_have_sell_l12m) {
		this.months_have_sell_l12m = months_have_sell_l12m;
	}

	public String getAverage_sell_count_l3m() {
		return average_sell_count_l3m;
	}

	public void setAverage_sell_count_l3m(String average_sell_count_l3m) {
		this.average_sell_count_l3m = average_sell_count_l3m;
	}

	public String getAverage_sell_count_l6m() {
		return average_sell_count_l6m;
	}

	public void setAverage_sell_count_l6m(String average_sell_count_l6m) {
		this.average_sell_count_l6m = average_sell_count_l6m;
	}

	public String getAverage_sell_count_l12m() {
		return average_sell_count_l12m;
	}

	public void setAverage_sell_count_l12m(String average_sell_count_l12m) {
		this.average_sell_count_l12m = average_sell_count_l12m;
	}

	public String getTimes_withdraw_deposit_l3m() {
		return times_withdraw_deposit_l3m;
	}

	public void setTimes_withdraw_deposit_l3m(String times_withdraw_deposit_l3m) {
		this.times_withdraw_deposit_l3m = times_withdraw_deposit_l3m;
	}

	public String getWithdraw_amount_l3m() {
		return withdraw_amount_l3m;
	}

	public void setWithdraw_amount_l3m(String withdraw_amount_l3m) {
		this.withdraw_amount_l3m = withdraw_amount_l3m;
	}

	public String getRatio_withdraw_months_div_all_months_l3m() {
		return ratio_withdraw_months_div_all_months_l3m;
	}

	public void setRatio_withdraw_months_div_all_months_l3m(String ratio_withdraw_months_div_all_months_l3m) {
		this.ratio_withdraw_months_div_all_months_l3m = ratio_withdraw_months_div_all_months_l3m;
	}

	public String getRatio_withdraw_months_div_all_months_l6m() {
		return ratio_withdraw_months_div_all_months_l6m;
	}

	public void setRatio_withdraw_months_div_all_months_l6m(String ratio_withdraw_months_div_all_months_l6m) {
		this.ratio_withdraw_months_div_all_months_l6m = ratio_withdraw_months_div_all_months_l6m;
	}

	public String getRatio_withdraw_months_div_all_months_l12m() {
		return ratio_withdraw_months_div_all_months_l12m;
	}

	public void setRatio_withdraw_months_div_all_months_l12m(String ratio_withdraw_months_div_all_months_l12m) {
		this.ratio_withdraw_months_div_all_months_l12m = ratio_withdraw_months_div_all_months_l12m;
	}

	public String getTotal_withdraw_count_l6m() {
		return total_withdraw_count_l6m;
	}

	public void setTotal_withdraw_count_l6m(String total_withdraw_count_l6m) {
		this.total_withdraw_count_l6m = total_withdraw_count_l6m;
	}

	public String getTotal_withdraw_count_l12m() {
		return total_withdraw_count_l12m;
	}

	public void setTotal_withdraw_count_l12m(String total_withdraw_count_l12m) {
		this.total_withdraw_count_l12m = total_withdraw_count_l12m;
	}

	public String getWithdraw_amount_l6m() {
		return withdraw_amount_l6m;
	}

	public void setWithdraw_amount_l6m(String withdraw_amount_l6m) {
		this.withdraw_amount_l6m = withdraw_amount_l6m;
	}

	public String getWithdraw_amount_l12m() {
		return withdraw_amount_l12m;
	}

	public void setWithdraw_amount_l12m(String withdraw_amount_l12m) {
		this.withdraw_amount_l12m = withdraw_amount_l12m;
	}

}

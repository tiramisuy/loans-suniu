package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.4 额度 (quota)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardQuota implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String average_balance_per_quota_nearly_3m;// 近3月平均余额占额度比例 0.10
	private String average_balance_per_quota_nearly_6m;// 近6月平均余额占额度比例 0.10
	private String average_balance_per_quota_nearly_12m;// 近12月平均余额占额度比例 0.04
	private String propertion_of_sales_amount_quota_3;// 近3月销售金额占额度比 1.33
	private String propertion_of_sales_amount_quota_6;// 近6月销售金额占额度比 1.33
	private String propertion_of_sales_amount_quota_12;// 近12月销售金额占额度比 1.33
	private String propertion_of_max_sales_amount_in_quota_3;// 近3月中最大销售金额占额度比
	private String propertion_of_max_sales_amount_in_quota_6;// 近6月中最大销售金额占额度比
	private String propertion_of_max_sales_amount_in_quota_12;// 近12月中最大销售金额占额度比
	private String propertion_of_min_sales_amount_in_quota_3;// 近3月中最小销售金额占额度比
	private String propertion_of_min_sales_amount_in_quota_6;// 近6月中最小销售金额占额度比
	private String propertion_of_min_sales_amount_in_quota_12;// 近12月中最小销售金额占额度比
	private String useage_of_max_quota_3;// 近3月最大额度使用率 43
	private String useage_of_max_quota_6;// 近6月最大额度使用率 43
	private String useage_of_max_quota_12;// 近12月最大额度使用率 43
	private String last_useage_more_than_0_nearly_3;// 近3月最近一次额度使用率>0的值 25.0
	private String last_useage_more_than_0_nearly_6;// 近6月最近一次额度使用率>0的值 25.0
	private String last_useage_more_than_0_nearly_12;// 近12月最近一次额度使用率>0的值 25.0
	private String avg_propertion_of_consume_withdrawal_in_quota_3;// 近3月平均（消费+提现）金额/信用额度（元）
	private String avg_propertion_of_consume_withdrawal_in_quota_6;// 近6月平均（消费+提现）金额/信用额度（元）
	private String avg_propertion_of_consume_withdrawal_in_quota_12;// 近12月平均（消费+提现）金额/信用额度（元）
	private String propertion_of_sales_amnt_in_quota_more_0_5_num_3;// 近3月中销售金额占额度比大于0.5的月数
	private String propertion_of_sales_amnt_in_quota_more_0_5_num_6;// 近6月中销售金额占额度比大于0.5的月数
	private String propertion_of_sales_amnt_in_quota_more_0_5_num_12;// 近12月中销售金额占额度比大于0.5的月数
	private String propertion_of_sales_amnt_in_quota_more_0_9_num_3;// 近3月中销售金额占额度比大于0.9的月数
	private String propertion_of_sales_amnt_in_quota_more_0_9_num_6;// 近6月中销售金额占额度比大于0.9的月数
	private String propertion_of_sales_amnt_in_quota_more_0_9_num_12;// 近12月中销售金额占额度比大于0.9的月数
	private String max_propertion_of_last_useage_3;// 最近一期额度使用率与近3月最大比 0.12
	private String max_propertion_of_last_useage_6;// 最近一期额度使用率与近6月最大比 0.45
	private String max_propertion_of_last_useage_12;// 最近一期额度使用率与近12月最大比 0.56
	private String avg_quota_3;// 近3月平均额度（元） 4000.00
	private String avg_quota_6;// 近6月平均额度（元） 2000.00
	private String avg_quota_12;// 近12月平均额度（元） 1000.00
	private String max_quota_3;// 近3月最高额度（元） 6000.00
	private String max_quota_6;// 近6月最高额度（元） 6000.00
	private String max_quota_12;// 近12月最高额度（元） 6000.00
	private String min_quota_3;// 近3月最低额度（元） 6000.00
	private String min_quota_6;// 近6月最低额度（元） 6000.00
	private String min_quota_12;// 近12月最低额度（元） 6000.00
	private String useage_of_min_quota_3;// 近3月最小额度使用率 21.3
	private String useage_of_min_quota_6;// 近6月最小额度使用率 15.6
	private String useage_of_min_quota_12;// 近12月最小额度使用率 28.4
	private String average_useage_rate_of_quota_nearly_3m;// 近3月平均额度使用率 14.33
	private String average_useage_rate_of_quota_nearly_6m;// 近6月平均额度使用率 7.17
	private String average_useage_rate_of_quota_nearly_12m;// 近12月平均额度使用率 3.58

	public String getAverage_balance_per_quota_nearly_3m() {
		return average_balance_per_quota_nearly_3m;
	}

	public void setAverage_balance_per_quota_nearly_3m(String average_balance_per_quota_nearly_3m) {
		this.average_balance_per_quota_nearly_3m = average_balance_per_quota_nearly_3m;
	}

	public String getAverage_balance_per_quota_nearly_6m() {
		return average_balance_per_quota_nearly_6m;
	}

	public void setAverage_balance_per_quota_nearly_6m(String average_balance_per_quota_nearly_6m) {
		this.average_balance_per_quota_nearly_6m = average_balance_per_quota_nearly_6m;
	}

	public String getAverage_balance_per_quota_nearly_12m() {
		return average_balance_per_quota_nearly_12m;
	}

	public void setAverage_balance_per_quota_nearly_12m(String average_balance_per_quota_nearly_12m) {
		this.average_balance_per_quota_nearly_12m = average_balance_per_quota_nearly_12m;
	}

	public String getPropertion_of_sales_amount_quota_3() {
		return propertion_of_sales_amount_quota_3;
	}

	public void setPropertion_of_sales_amount_quota_3(String propertion_of_sales_amount_quota_3) {
		this.propertion_of_sales_amount_quota_3 = propertion_of_sales_amount_quota_3;
	}

	public String getPropertion_of_sales_amount_quota_6() {
		return propertion_of_sales_amount_quota_6;
	}

	public void setPropertion_of_sales_amount_quota_6(String propertion_of_sales_amount_quota_6) {
		this.propertion_of_sales_amount_quota_6 = propertion_of_sales_amount_quota_6;
	}

	public String getPropertion_of_sales_amount_quota_12() {
		return propertion_of_sales_amount_quota_12;
	}

	public void setPropertion_of_sales_amount_quota_12(String propertion_of_sales_amount_quota_12) {
		this.propertion_of_sales_amount_quota_12 = propertion_of_sales_amount_quota_12;
	}

	public String getPropertion_of_max_sales_amount_in_quota_3() {
		return propertion_of_max_sales_amount_in_quota_3;
	}

	public void setPropertion_of_max_sales_amount_in_quota_3(String propertion_of_max_sales_amount_in_quota_3) {
		this.propertion_of_max_sales_amount_in_quota_3 = propertion_of_max_sales_amount_in_quota_3;
	}

	public String getPropertion_of_max_sales_amount_in_quota_6() {
		return propertion_of_max_sales_amount_in_quota_6;
	}

	public void setPropertion_of_max_sales_amount_in_quota_6(String propertion_of_max_sales_amount_in_quota_6) {
		this.propertion_of_max_sales_amount_in_quota_6 = propertion_of_max_sales_amount_in_quota_6;
	}

	public String getPropertion_of_max_sales_amount_in_quota_12() {
		return propertion_of_max_sales_amount_in_quota_12;
	}

	public void setPropertion_of_max_sales_amount_in_quota_12(String propertion_of_max_sales_amount_in_quota_12) {
		this.propertion_of_max_sales_amount_in_quota_12 = propertion_of_max_sales_amount_in_quota_12;
	}

	public String getPropertion_of_min_sales_amount_in_quota_3() {
		return propertion_of_min_sales_amount_in_quota_3;
	}

	public void setPropertion_of_min_sales_amount_in_quota_3(String propertion_of_min_sales_amount_in_quota_3) {
		this.propertion_of_min_sales_amount_in_quota_3 = propertion_of_min_sales_amount_in_quota_3;
	}

	public String getPropertion_of_min_sales_amount_in_quota_6() {
		return propertion_of_min_sales_amount_in_quota_6;
	}

	public void setPropertion_of_min_sales_amount_in_quota_6(String propertion_of_min_sales_amount_in_quota_6) {
		this.propertion_of_min_sales_amount_in_quota_6 = propertion_of_min_sales_amount_in_quota_6;
	}

	public String getPropertion_of_min_sales_amount_in_quota_12() {
		return propertion_of_min_sales_amount_in_quota_12;
	}

	public void setPropertion_of_min_sales_amount_in_quota_12(String propertion_of_min_sales_amount_in_quota_12) {
		this.propertion_of_min_sales_amount_in_quota_12 = propertion_of_min_sales_amount_in_quota_12;
	}

	public String getUseage_of_max_quota_3() {
		return useage_of_max_quota_3;
	}

	public void setUseage_of_max_quota_3(String useage_of_max_quota_3) {
		this.useage_of_max_quota_3 = useage_of_max_quota_3;
	}

	public String getUseage_of_max_quota_6() {
		return useage_of_max_quota_6;
	}

	public void setUseage_of_max_quota_6(String useage_of_max_quota_6) {
		this.useage_of_max_quota_6 = useage_of_max_quota_6;
	}

	public String getUseage_of_max_quota_12() {
		return useage_of_max_quota_12;
	}

	public void setUseage_of_max_quota_12(String useage_of_max_quota_12) {
		this.useage_of_max_quota_12 = useage_of_max_quota_12;
	}

	public String getLast_useage_more_than_0_nearly_3() {
		return last_useage_more_than_0_nearly_3;
	}

	public void setLast_useage_more_than_0_nearly_3(String last_useage_more_than_0_nearly_3) {
		this.last_useage_more_than_0_nearly_3 = last_useage_more_than_0_nearly_3;
	}

	public String getLast_useage_more_than_0_nearly_6() {
		return last_useage_more_than_0_nearly_6;
	}

	public void setLast_useage_more_than_0_nearly_6(String last_useage_more_than_0_nearly_6) {
		this.last_useage_more_than_0_nearly_6 = last_useage_more_than_0_nearly_6;
	}

	public String getLast_useage_more_than_0_nearly_12() {
		return last_useage_more_than_0_nearly_12;
	}

	public void setLast_useage_more_than_0_nearly_12(String last_useage_more_than_0_nearly_12) {
		this.last_useage_more_than_0_nearly_12 = last_useage_more_than_0_nearly_12;
	}

	public String getAvg_propertion_of_consume_withdrawal_in_quota_3() {
		return avg_propertion_of_consume_withdrawal_in_quota_3;
	}

	public void setAvg_propertion_of_consume_withdrawal_in_quota_3(
			String avg_propertion_of_consume_withdrawal_in_quota_3) {
		this.avg_propertion_of_consume_withdrawal_in_quota_3 = avg_propertion_of_consume_withdrawal_in_quota_3;
	}

	public String getAvg_propertion_of_consume_withdrawal_in_quota_6() {
		return avg_propertion_of_consume_withdrawal_in_quota_6;
	}

	public void setAvg_propertion_of_consume_withdrawal_in_quota_6(
			String avg_propertion_of_consume_withdrawal_in_quota_6) {
		this.avg_propertion_of_consume_withdrawal_in_quota_6 = avg_propertion_of_consume_withdrawal_in_quota_6;
	}

	public String getAvg_propertion_of_consume_withdrawal_in_quota_12() {
		return avg_propertion_of_consume_withdrawal_in_quota_12;
	}

	public void setAvg_propertion_of_consume_withdrawal_in_quota_12(
			String avg_propertion_of_consume_withdrawal_in_quota_12) {
		this.avg_propertion_of_consume_withdrawal_in_quota_12 = avg_propertion_of_consume_withdrawal_in_quota_12;
	}

	public String getPropertion_of_sales_amnt_in_quota_more_0_5_num_3() {
		return propertion_of_sales_amnt_in_quota_more_0_5_num_3;
	}

	public void setPropertion_of_sales_amnt_in_quota_more_0_5_num_3(
			String propertion_of_sales_amnt_in_quota_more_0_5_num_3) {
		this.propertion_of_sales_amnt_in_quota_more_0_5_num_3 = propertion_of_sales_amnt_in_quota_more_0_5_num_3;
	}

	public String getPropertion_of_sales_amnt_in_quota_more_0_5_num_6() {
		return propertion_of_sales_amnt_in_quota_more_0_5_num_6;
	}

	public void setPropertion_of_sales_amnt_in_quota_more_0_5_num_6(
			String propertion_of_sales_amnt_in_quota_more_0_5_num_6) {
		this.propertion_of_sales_amnt_in_quota_more_0_5_num_6 = propertion_of_sales_amnt_in_quota_more_0_5_num_6;
	}

	public String getPropertion_of_sales_amnt_in_quota_more_0_5_num_12() {
		return propertion_of_sales_amnt_in_quota_more_0_5_num_12;
	}

	public void setPropertion_of_sales_amnt_in_quota_more_0_5_num_12(
			String propertion_of_sales_amnt_in_quota_more_0_5_num_12) {
		this.propertion_of_sales_amnt_in_quota_more_0_5_num_12 = propertion_of_sales_amnt_in_quota_more_0_5_num_12;
	}

	public String getPropertion_of_sales_amnt_in_quota_more_0_9_num_3() {
		return propertion_of_sales_amnt_in_quota_more_0_9_num_3;
	}

	public void setPropertion_of_sales_amnt_in_quota_more_0_9_num_3(
			String propertion_of_sales_amnt_in_quota_more_0_9_num_3) {
		this.propertion_of_sales_amnt_in_quota_more_0_9_num_3 = propertion_of_sales_amnt_in_quota_more_0_9_num_3;
	}

	public String getPropertion_of_sales_amnt_in_quota_more_0_9_num_6() {
		return propertion_of_sales_amnt_in_quota_more_0_9_num_6;
	}

	public void setPropertion_of_sales_amnt_in_quota_more_0_9_num_6(
			String propertion_of_sales_amnt_in_quota_more_0_9_num_6) {
		this.propertion_of_sales_amnt_in_quota_more_0_9_num_6 = propertion_of_sales_amnt_in_quota_more_0_9_num_6;
	}

	public String getPropertion_of_sales_amnt_in_quota_more_0_9_num_12() {
		return propertion_of_sales_amnt_in_quota_more_0_9_num_12;
	}

	public void setPropertion_of_sales_amnt_in_quota_more_0_9_num_12(
			String propertion_of_sales_amnt_in_quota_more_0_9_num_12) {
		this.propertion_of_sales_amnt_in_quota_more_0_9_num_12 = propertion_of_sales_amnt_in_quota_more_0_9_num_12;
	}

	public String getMax_propertion_of_last_useage_3() {
		return max_propertion_of_last_useage_3;
	}

	public void setMax_propertion_of_last_useage_3(String max_propertion_of_last_useage_3) {
		this.max_propertion_of_last_useage_3 = max_propertion_of_last_useage_3;
	}

	public String getMax_propertion_of_last_useage_6() {
		return max_propertion_of_last_useage_6;
	}

	public void setMax_propertion_of_last_useage_6(String max_propertion_of_last_useage_6) {
		this.max_propertion_of_last_useage_6 = max_propertion_of_last_useage_6;
	}

	public String getMax_propertion_of_last_useage_12() {
		return max_propertion_of_last_useage_12;
	}

	public void setMax_propertion_of_last_useage_12(String max_propertion_of_last_useage_12) {
		this.max_propertion_of_last_useage_12 = max_propertion_of_last_useage_12;
	}

	public String getAvg_quota_3() {
		return avg_quota_3;
	}

	public void setAvg_quota_3(String avg_quota_3) {
		this.avg_quota_3 = avg_quota_3;
	}

	public String getAvg_quota_6() {
		return avg_quota_6;
	}

	public void setAvg_quota_6(String avg_quota_6) {
		this.avg_quota_6 = avg_quota_6;
	}

	public String getAvg_quota_12() {
		return avg_quota_12;
	}

	public void setAvg_quota_12(String avg_quota_12) {
		this.avg_quota_12 = avg_quota_12;
	}

	public String getMax_quota_3() {
		return max_quota_3;
	}

	public void setMax_quota_3(String max_quota_3) {
		this.max_quota_3 = max_quota_3;
	}

	public String getMax_quota_6() {
		return max_quota_6;
	}

	public void setMax_quota_6(String max_quota_6) {
		this.max_quota_6 = max_quota_6;
	}

	public String getMax_quota_12() {
		return max_quota_12;
	}

	public void setMax_quota_12(String max_quota_12) {
		this.max_quota_12 = max_quota_12;
	}

	public String getMin_quota_3() {
		return min_quota_3;
	}

	public void setMin_quota_3(String min_quota_3) {
		this.min_quota_3 = min_quota_3;
	}

	public String getMin_quota_6() {
		return min_quota_6;
	}

	public void setMin_quota_6(String min_quota_6) {
		this.min_quota_6 = min_quota_6;
	}

	public String getMin_quota_12() {
		return min_quota_12;
	}

	public void setMin_quota_12(String min_quota_12) {
		this.min_quota_12 = min_quota_12;
	}

	public String getUseage_of_min_quota_3() {
		return useage_of_min_quota_3;
	}

	public void setUseage_of_min_quota_3(String useage_of_min_quota_3) {
		this.useage_of_min_quota_3 = useage_of_min_quota_3;
	}

	public String getUseage_of_min_quota_6() {
		return useage_of_min_quota_6;
	}

	public void setUseage_of_min_quota_6(String useage_of_min_quota_6) {
		this.useage_of_min_quota_6 = useage_of_min_quota_6;
	}

	public String getUseage_of_min_quota_12() {
		return useage_of_min_quota_12;
	}

	public void setUseage_of_min_quota_12(String useage_of_min_quota_12) {
		this.useage_of_min_quota_12 = useage_of_min_quota_12;
	}

	public String getAverage_useage_rate_of_quota_nearly_3m() {
		return average_useage_rate_of_quota_nearly_3m;
	}

	public void setAverage_useage_rate_of_quota_nearly_3m(String average_useage_rate_of_quota_nearly_3m) {
		this.average_useage_rate_of_quota_nearly_3m = average_useage_rate_of_quota_nearly_3m;
	}

	public String getAverage_useage_rate_of_quota_nearly_6m() {
		return average_useage_rate_of_quota_nearly_6m;
	}

	public void setAverage_useage_rate_of_quota_nearly_6m(String average_useage_rate_of_quota_nearly_6m) {
		this.average_useage_rate_of_quota_nearly_6m = average_useage_rate_of_quota_nearly_6m;
	}

	public String getAverage_useage_rate_of_quota_nearly_12m() {
		return average_useage_rate_of_quota_nearly_12m;
	}

	public void setAverage_useage_rate_of_quota_nearly_12m(String average_useage_rate_of_quota_nearly_12m) {
		this.average_useage_rate_of_quota_nearly_12m = average_useage_rate_of_quota_nearly_12m;
	}

}

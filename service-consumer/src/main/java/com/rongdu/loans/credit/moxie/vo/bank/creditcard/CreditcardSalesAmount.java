package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.1 销售金额 (sales_amount)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardSalesAmount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ratio_sell_month_3;// 近3月销售金额大于0的月份占比 1.00
	private String ratio_sell_month_6;// 近6月销售金额大于0的月份占比 1.00
	private String ratio_sell_month_12;// 近12月销售金额大于0的月份占比 1.00
	private String avg_consume_amount_3;// 近3月月均消费金额（元） 148.00
	private String avg_consume_amount_6;// 近6月月均消费金额（元） 1681.32
	private String avg_consume_amount_12;// 近12月月均消费金额（元） 4667.70
	private String creditcard_max_balance_3;// 近3月最高消费金额（元） 148.0
	private String creditcard_max_balance_6;// 近6月最高消费金额（元） 148.0
	private String creditcard_max_balance_12;// 近12月最高消费金额（元） 148.0
	private String creditcard_min_balance_3;// 近3月最低消费金额（元） 148.0
	private String creditcard_min_balance_6;// 近6月最低消费金额（元） 148.0
	private String creditcard_min_balance_12;// 近12月最低消费金额（元） 148.0
	private String withdraw_num_cc_3;// 近3月提现次数 3
	private String withdraw_num_cc_6;// 近6月提现次数 6
	private String withdraw_num_cc_12;// 近12月提现次数 12
	private String withdraw_amount_3;// 近3月提现金额（元） 20.00
	private String withdraw_amount_6;// 近6月提现金额（元） 20.00
	private String withdraw_amount_12;// 近12月提现金额（元） 20.00
	private String has_withdrawal_percentage_3m;// 近3月有提现月数占比 0.33
	private String has_withdrawal_percentage_6m;// 近6月有提现月数占比 0.17
	private String has_withdrawal_percentage_12m;// 近12月有提现月数占比 0.88
	private String avg_cash_amount_3;// 近3月月均提现金额（元） 467.90
	private String avg_cash_amount_6;// 近6月月均提现金额（元） 234.56
	private String avg_cash_amount_12;// 近12月月均提现金额（元） 547.78
	private String last_withdrawal_month_num_3;// 近3月最近一次提现距今的月数 2
	private String last_withdrawal_month_num_6;// 近6月最近一次提现距今的月数 3
	private String last_withdrawal_month_num_12;// 近12月最近一次提现距今的月数 4
	private String earlyest_sell_amount_3;// 近3月首次销售金额（元） 148.0
	private String earlyest_sell_amount_6;// 近6月首次销售金额（元） 1818.0
	private String earlyest_sell_amount_12;// 近12月首次销售金额（元） 148.0
	private String earlyest_consume_amount_3;// 近3月首次消费金额（元） 148.0
	private String earlyest_consume_amount_6;// 近6月首次消费金额（元） 148.0
	private String earlyest_consume_amount_12;// 近12月首次消费金额（元） 148.0
	private String max_sell_num_3;// 近3月单期最大销售笔数 1
	private String max_sell_num_6;// 近6月单期最大销售笔数 12
	private String max_sell_num_12;// 近12月单期最大销售笔数 23
	private String has_sell_month_num_3;// 近3月有销售的月数 3
	private String has_sell_month_num_6;// 近6月有销售的月数 6
	private String has_sell_month_num_12;// 近12月有销售的月数 12
	private String avg_consume_num_3;// 近3月月均消费笔数 1.00
	private String avg_consume_num_6;// 近6月月均消费笔数 2.83
	private String avg_consume_num_12;// 近12月月均消费笔数 6.50

	public String getRatio_sell_month_3() {
		return ratio_sell_month_3;
	}

	public void setRatio_sell_month_3(String ratio_sell_month_3) {
		this.ratio_sell_month_3 = ratio_sell_month_3;
	}

	public String getRatio_sell_month_6() {
		return ratio_sell_month_6;
	}

	public void setRatio_sell_month_6(String ratio_sell_month_6) {
		this.ratio_sell_month_6 = ratio_sell_month_6;
	}

	public String getRatio_sell_month_12() {
		return ratio_sell_month_12;
	}

	public void setRatio_sell_month_12(String ratio_sell_month_12) {
		this.ratio_sell_month_12 = ratio_sell_month_12;
	}

	public String getAvg_consume_amount_3() {
		return avg_consume_amount_3;
	}

	public void setAvg_consume_amount_3(String avg_consume_amount_3) {
		this.avg_consume_amount_3 = avg_consume_amount_3;
	}

	public String getAvg_consume_amount_6() {
		return avg_consume_amount_6;
	}

	public void setAvg_consume_amount_6(String avg_consume_amount_6) {
		this.avg_consume_amount_6 = avg_consume_amount_6;
	}

	public String getAvg_consume_amount_12() {
		return avg_consume_amount_12;
	}

	public void setAvg_consume_amount_12(String avg_consume_amount_12) {
		this.avg_consume_amount_12 = avg_consume_amount_12;
	}

	public String getCreditcard_max_balance_3() {
		return creditcard_max_balance_3;
	}

	public void setCreditcard_max_balance_3(String creditcard_max_balance_3) {
		this.creditcard_max_balance_3 = creditcard_max_balance_3;
	}

	public String getCreditcard_max_balance_6() {
		return creditcard_max_balance_6;
	}

	public void setCreditcard_max_balance_6(String creditcard_max_balance_6) {
		this.creditcard_max_balance_6 = creditcard_max_balance_6;
	}

	public String getCreditcard_max_balance_12() {
		return creditcard_max_balance_12;
	}

	public void setCreditcard_max_balance_12(String creditcard_max_balance_12) {
		this.creditcard_max_balance_12 = creditcard_max_balance_12;
	}

	public String getCreditcard_min_balance_3() {
		return creditcard_min_balance_3;
	}

	public void setCreditcard_min_balance_3(String creditcard_min_balance_3) {
		this.creditcard_min_balance_3 = creditcard_min_balance_3;
	}

	public String getCreditcard_min_balance_6() {
		return creditcard_min_balance_6;
	}

	public void setCreditcard_min_balance_6(String creditcard_min_balance_6) {
		this.creditcard_min_balance_6 = creditcard_min_balance_6;
	}

	public String getCreditcard_min_balance_12() {
		return creditcard_min_balance_12;
	}

	public void setCreditcard_min_balance_12(String creditcard_min_balance_12) {
		this.creditcard_min_balance_12 = creditcard_min_balance_12;
	}

	public String getWithdraw_num_cc_3() {
		return withdraw_num_cc_3;
	}

	public void setWithdraw_num_cc_3(String withdraw_num_cc_3) {
		this.withdraw_num_cc_3 = withdraw_num_cc_3;
	}

	public String getWithdraw_num_cc_6() {
		return withdraw_num_cc_6;
	}

	public void setWithdraw_num_cc_6(String withdraw_num_cc_6) {
		this.withdraw_num_cc_6 = withdraw_num_cc_6;
	}

	public String getWithdraw_num_cc_12() {
		return withdraw_num_cc_12;
	}

	public void setWithdraw_num_cc_12(String withdraw_num_cc_12) {
		this.withdraw_num_cc_12 = withdraw_num_cc_12;
	}

	public String getWithdraw_amount_3() {
		return withdraw_amount_3;
	}

	public void setWithdraw_amount_3(String withdraw_amount_3) {
		this.withdraw_amount_3 = withdraw_amount_3;
	}

	public String getWithdraw_amount_6() {
		return withdraw_amount_6;
	}

	public void setWithdraw_amount_6(String withdraw_amount_6) {
		this.withdraw_amount_6 = withdraw_amount_6;
	}

	public String getWithdraw_amount_12() {
		return withdraw_amount_12;
	}

	public void setWithdraw_amount_12(String withdraw_amount_12) {
		this.withdraw_amount_12 = withdraw_amount_12;
	}

	public String getHas_withdrawal_percentage_3m() {
		return has_withdrawal_percentage_3m;
	}

	public void setHas_withdrawal_percentage_3m(String has_withdrawal_percentage_3m) {
		this.has_withdrawal_percentage_3m = has_withdrawal_percentage_3m;
	}

	public String getHas_withdrawal_percentage_6m() {
		return has_withdrawal_percentage_6m;
	}

	public void setHas_withdrawal_percentage_6m(String has_withdrawal_percentage_6m) {
		this.has_withdrawal_percentage_6m = has_withdrawal_percentage_6m;
	}

	public String getHas_withdrawal_percentage_12m() {
		return has_withdrawal_percentage_12m;
	}

	public void setHas_withdrawal_percentage_12m(String has_withdrawal_percentage_12m) {
		this.has_withdrawal_percentage_12m = has_withdrawal_percentage_12m;
	}

	public String getAvg_cash_amount_3() {
		return avg_cash_amount_3;
	}

	public void setAvg_cash_amount_3(String avg_cash_amount_3) {
		this.avg_cash_amount_3 = avg_cash_amount_3;
	}

	public String getAvg_cash_amount_6() {
		return avg_cash_amount_6;
	}

	public void setAvg_cash_amount_6(String avg_cash_amount_6) {
		this.avg_cash_amount_6 = avg_cash_amount_6;
	}

	public String getAvg_cash_amount_12() {
		return avg_cash_amount_12;
	}

	public void setAvg_cash_amount_12(String avg_cash_amount_12) {
		this.avg_cash_amount_12 = avg_cash_amount_12;
	}

	public String getLast_withdrawal_month_num_3() {
		return last_withdrawal_month_num_3;
	}

	public void setLast_withdrawal_month_num_3(String last_withdrawal_month_num_3) {
		this.last_withdrawal_month_num_3 = last_withdrawal_month_num_3;
	}

	public String getLast_withdrawal_month_num_6() {
		return last_withdrawal_month_num_6;
	}

	public void setLast_withdrawal_month_num_6(String last_withdrawal_month_num_6) {
		this.last_withdrawal_month_num_6 = last_withdrawal_month_num_6;
	}

	public String getLast_withdrawal_month_num_12() {
		return last_withdrawal_month_num_12;
	}

	public void setLast_withdrawal_month_num_12(String last_withdrawal_month_num_12) {
		this.last_withdrawal_month_num_12 = last_withdrawal_month_num_12;
	}

	public String getEarlyest_sell_amount_3() {
		return earlyest_sell_amount_3;
	}

	public void setEarlyest_sell_amount_3(String earlyest_sell_amount_3) {
		this.earlyest_sell_amount_3 = earlyest_sell_amount_3;
	}

	public String getEarlyest_sell_amount_6() {
		return earlyest_sell_amount_6;
	}

	public void setEarlyest_sell_amount_6(String earlyest_sell_amount_6) {
		this.earlyest_sell_amount_6 = earlyest_sell_amount_6;
	}

	public String getEarlyest_sell_amount_12() {
		return earlyest_sell_amount_12;
	}

	public void setEarlyest_sell_amount_12(String earlyest_sell_amount_12) {
		this.earlyest_sell_amount_12 = earlyest_sell_amount_12;
	}

	public String getEarlyest_consume_amount_3() {
		return earlyest_consume_amount_3;
	}

	public void setEarlyest_consume_amount_3(String earlyest_consume_amount_3) {
		this.earlyest_consume_amount_3 = earlyest_consume_amount_3;
	}

	public String getEarlyest_consume_amount_6() {
		return earlyest_consume_amount_6;
	}

	public void setEarlyest_consume_amount_6(String earlyest_consume_amount_6) {
		this.earlyest_consume_amount_6 = earlyest_consume_amount_6;
	}

	public String getEarlyest_consume_amount_12() {
		return earlyest_consume_amount_12;
	}

	public void setEarlyest_consume_amount_12(String earlyest_consume_amount_12) {
		this.earlyest_consume_amount_12 = earlyest_consume_amount_12;
	}

	public String getMax_sell_num_3() {
		return max_sell_num_3;
	}

	public void setMax_sell_num_3(String max_sell_num_3) {
		this.max_sell_num_3 = max_sell_num_3;
	}

	public String getMax_sell_num_6() {
		return max_sell_num_6;
	}

	public void setMax_sell_num_6(String max_sell_num_6) {
		this.max_sell_num_6 = max_sell_num_6;
	}

	public String getMax_sell_num_12() {
		return max_sell_num_12;
	}

	public void setMax_sell_num_12(String max_sell_num_12) {
		this.max_sell_num_12 = max_sell_num_12;
	}

	public String getHas_sell_month_num_3() {
		return has_sell_month_num_3;
	}

	public void setHas_sell_month_num_3(String has_sell_month_num_3) {
		this.has_sell_month_num_3 = has_sell_month_num_3;
	}

	public String getHas_sell_month_num_6() {
		return has_sell_month_num_6;
	}

	public void setHas_sell_month_num_6(String has_sell_month_num_6) {
		this.has_sell_month_num_6 = has_sell_month_num_6;
	}

	public String getHas_sell_month_num_12() {
		return has_sell_month_num_12;
	}

	public void setHas_sell_month_num_12(String has_sell_month_num_12) {
		this.has_sell_month_num_12 = has_sell_month_num_12;
	}

	public String getAvg_consume_num_3() {
		return avg_consume_num_3;
	}

	public void setAvg_consume_num_3(String avg_consume_num_3) {
		this.avg_consume_num_3 = avg_consume_num_3;
	}

	public String getAvg_consume_num_6() {
		return avg_consume_num_6;
	}

	public void setAvg_consume_num_6(String avg_consume_num_6) {
		this.avg_consume_num_6 = avg_consume_num_6;
	}

	public String getAvg_consume_num_12() {
		return avg_consume_num_12;
	}

	public void setAvg_consume_num_12(String avg_consume_num_12) {
		this.avg_consume_num_12 = avg_consume_num_12;
	}

}

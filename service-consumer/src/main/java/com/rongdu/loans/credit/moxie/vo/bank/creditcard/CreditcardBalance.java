package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.2 余额 (balance)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardBalance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String retail_balance_1_per_3;// 近1月零售余额/近3月均零售余额（元） 1.68
	private String retail_balance_1_per_6;// 近1月零售余额/近6月均零售余额（元） 3.35
	private String retail_balance_1_per_12;// 近1月零售余额/近12月均零售余额（元） 6.71
	private String avg_amount_per_avg_balance_3;// 近3月均销售占近3月均余额比率 3.89
	private String avg_amount_per_avg_balance_6;// 近6月均销售占近3月均余额比率 2.34
	private String avg_amount_per_avg_balance_12;// 近12月均销售占近3月均余额比率 1.56
	private String max_balance_month_num_3;// 近3月最高余额距今的月数 1
	private String max_balance_month_num_6;// 近6月最高余额距今的月数 1
	private String max_balance_month_num_12;// 近12月最高余额距今的月数 1

	public String getRetail_balance_1_per_3() {
		return retail_balance_1_per_3;
	}

	public void setRetail_balance_1_per_3(String retail_balance_1_per_3) {
		this.retail_balance_1_per_3 = retail_balance_1_per_3;
	}

	public String getRetail_balance_1_per_6() {
		return retail_balance_1_per_6;
	}

	public void setRetail_balance_1_per_6(String retail_balance_1_per_6) {
		this.retail_balance_1_per_6 = retail_balance_1_per_6;
	}

	public String getRetail_balance_1_per_12() {
		return retail_balance_1_per_12;
	}

	public void setRetail_balance_1_per_12(String retail_balance_1_per_12) {
		this.retail_balance_1_per_12 = retail_balance_1_per_12;
	}

	public String getAvg_amount_per_avg_balance_3() {
		return avg_amount_per_avg_balance_3;
	}

	public void setAvg_amount_per_avg_balance_3(String avg_amount_per_avg_balance_3) {
		this.avg_amount_per_avg_balance_3 = avg_amount_per_avg_balance_3;
	}

	public String getAvg_amount_per_avg_balance_6() {
		return avg_amount_per_avg_balance_6;
	}

	public void setAvg_amount_per_avg_balance_6(String avg_amount_per_avg_balance_6) {
		this.avg_amount_per_avg_balance_6 = avg_amount_per_avg_balance_6;
	}

	public String getAvg_amount_per_avg_balance_12() {
		return avg_amount_per_avg_balance_12;
	}

	public void setAvg_amount_per_avg_balance_12(String avg_amount_per_avg_balance_12) {
		this.avg_amount_per_avg_balance_12 = avg_amount_per_avg_balance_12;
	}

	public String getMax_balance_month_num_3() {
		return max_balance_month_num_3;
	}

	public void setMax_balance_month_num_3(String max_balance_month_num_3) {
		this.max_balance_month_num_3 = max_balance_month_num_3;
	}

	public String getMax_balance_month_num_6() {
		return max_balance_month_num_6;
	}

	public void setMax_balance_month_num_6(String max_balance_month_num_6) {
		this.max_balance_month_num_6 = max_balance_month_num_6;
	}

	public String getMax_balance_month_num_12() {
		return max_balance_month_num_12;
	}

	public void setMax_balance_month_num_12(String max_balance_month_num_12) {
		this.max_balance_month_num_12 = max_balance_month_num_12;
	}

}

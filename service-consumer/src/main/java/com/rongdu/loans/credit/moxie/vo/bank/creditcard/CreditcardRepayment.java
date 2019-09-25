package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.3 还款 (repayment)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardRepayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String avg_pay_amount_3;// 近3月平均还款金额（元） 2974.62
	private String avg_pay_amount_6;// 近6月平均还款金额（元） 1487.31
	private String avg_pay_amount_12;// 近12月平均还款金额（元） 743.65
	private String repay_ratio_avg_3;// 近3月平均还款率 33.33
	private String repay_ratio_avg_6;// 近6月平均还款率 16.67
	private String repay_ratio_avg_12;// 近12月平均还款率 8.33
	private String last_repay_now_num_3;// 近3月最近一次产生还款金额距今的月数 1
	private String last_repay_now_num_6;// 近6月最近一次产生还款金额距今的月数 1
	private String last_repay_now_num_12;// 近12月最近一次产生还款金额距今的月数 1
	private String minpay_mons_3;// 近3月有MINPAY且不足全额的月数 1
	private String minpay_mons_6;// 近6月有MINPAY且不足全额的月数 1
	private String minpay_mons_12;// 近12月有MINPAY且不足全额的月数 1

	public String getAvg_pay_amount_3() {
		return avg_pay_amount_3;
	}

	public void setAvg_pay_amount_3(String avg_pay_amount_3) {
		this.avg_pay_amount_3 = avg_pay_amount_3;
	}

	public String getAvg_pay_amount_6() {
		return avg_pay_amount_6;
	}

	public void setAvg_pay_amount_6(String avg_pay_amount_6) {
		this.avg_pay_amount_6 = avg_pay_amount_6;
	}

	public String getAvg_pay_amount_12() {
		return avg_pay_amount_12;
	}

	public void setAvg_pay_amount_12(String avg_pay_amount_12) {
		this.avg_pay_amount_12 = avg_pay_amount_12;
	}

	public String getRepay_ratio_avg_3() {
		return repay_ratio_avg_3;
	}

	public void setRepay_ratio_avg_3(String repay_ratio_avg_3) {
		this.repay_ratio_avg_3 = repay_ratio_avg_3;
	}

	public String getRepay_ratio_avg_6() {
		return repay_ratio_avg_6;
	}

	public void setRepay_ratio_avg_6(String repay_ratio_avg_6) {
		this.repay_ratio_avg_6 = repay_ratio_avg_6;
	}

	public String getRepay_ratio_avg_12() {
		return repay_ratio_avg_12;
	}

	public void setRepay_ratio_avg_12(String repay_ratio_avg_12) {
		this.repay_ratio_avg_12 = repay_ratio_avg_12;
	}

	public String getLast_repay_now_num_3() {
		return last_repay_now_num_3;
	}

	public void setLast_repay_now_num_3(String last_repay_now_num_3) {
		this.last_repay_now_num_3 = last_repay_now_num_3;
	}

	public String getLast_repay_now_num_6() {
		return last_repay_now_num_6;
	}

	public void setLast_repay_now_num_6(String last_repay_now_num_6) {
		this.last_repay_now_num_6 = last_repay_now_num_6;
	}

	public String getLast_repay_now_num_12() {
		return last_repay_now_num_12;
	}

	public void setLast_repay_now_num_12(String last_repay_now_num_12) {
		this.last_repay_now_num_12 = last_repay_now_num_12;
	}

	public String getMinpay_mons_3() {
		return minpay_mons_3;
	}

	public void setMinpay_mons_3(String minpay_mons_3) {
		this.minpay_mons_3 = minpay_mons_3;
	}

	public String getMinpay_mons_6() {
		return minpay_mons_6;
	}

	public void setMinpay_mons_6(String minpay_mons_6) {
		this.minpay_mons_6 = minpay_mons_6;
	}

	public String getMinpay_mons_12() {
		return minpay_mons_12;
	}

	public void setMinpay_mons_12(String minpay_mons_12) {
		this.minpay_mons_12 = minpay_mons_12;
	}

}

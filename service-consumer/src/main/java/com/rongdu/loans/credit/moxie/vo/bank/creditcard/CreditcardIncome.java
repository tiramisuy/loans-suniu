package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.6 收入 (income)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardIncome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String credit_incom_avg_3;// 近3月平均收入（元） 63.19
	private String credit_incom_avg_6;// 近6月平均收入（元） 31.60
	private String credit_incom_avg_12;// 近12月平均收入（元） 15.80
	private String income_avg_3_div_6;// 近3个月平均收入与近6个月平均比 2.00
	private String income_avg_6_div_12;// 近6个月平均收入与近12个月平均比 2.00
	private String min_income_now_mons_3;// 近3月最低收入距今月份数 1
	private String min_income_now_mons_6;// 近6月最低收入距今月份数 1
	private String min_income_now_mons_12;// 近12月最低收入距今月份数 1

	public String getCredit_incom_avg_3() {
		return credit_incom_avg_3;
	}

	public void setCredit_incom_avg_3(String credit_incom_avg_3) {
		this.credit_incom_avg_3 = credit_incom_avg_3;
	}

	public String getCredit_incom_avg_6() {
		return credit_incom_avg_6;
	}

	public void setCredit_incom_avg_6(String credit_incom_avg_6) {
		this.credit_incom_avg_6 = credit_incom_avg_6;
	}

	public String getCredit_incom_avg_12() {
		return credit_incom_avg_12;
	}

	public void setCredit_incom_avg_12(String credit_incom_avg_12) {
		this.credit_incom_avg_12 = credit_incom_avg_12;
	}

	public String getIncome_avg_3_div_6() {
		return income_avg_3_div_6;
	}

	public void setIncome_avg_3_div_6(String income_avg_3_div_6) {
		this.income_avg_3_div_6 = income_avg_3_div_6;
	}

	public String getIncome_avg_6_div_12() {
		return income_avg_6_div_12;
	}

	public void setIncome_avg_6_div_12(String income_avg_6_div_12) {
		this.income_avg_6_div_12 = income_avg_6_div_12;
	}

	public String getMin_income_now_mons_3() {
		return min_income_now_mons_3;
	}

	public void setMin_income_now_mons_3(String min_income_now_mons_3) {
		this.min_income_now_mons_3 = min_income_now_mons_3;
	}

	public String getMin_income_now_mons_6() {
		return min_income_now_mons_6;
	}

	public void setMin_income_now_mons_6(String min_income_now_mons_6) {
		this.min_income_now_mons_6 = min_income_now_mons_6;
	}

	public String getMin_income_now_mons_12() {
		return min_income_now_mons_12;
	}

	public void setMin_income_now_mons_12(String min_income_now_mons_12) {
		this.min_income_now_mons_12 = min_income_now_mons_12;
	}

}

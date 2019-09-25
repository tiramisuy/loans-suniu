package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 1.6 利费信息 (interest_information)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardInterestInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String delay_interest_1;// 总利息（元） 23.00
	private String overdue_amount_1;// 延滞金（元） 12.49
	private String overdue_pay_1;// 超额金（元） 43.89
	private String other_fee_1;// 其他费用（元） 12.54
	private String income_amt_1;// 收入 3421.78

	public String getDelay_interest_1() {
		return delay_interest_1;
	}

	public void setDelay_interest_1(String delay_interest_1) {
		this.delay_interest_1 = delay_interest_1;
	}

	public String getOverdue_amount_1() {
		return overdue_amount_1;
	}

	public void setOverdue_amount_1(String overdue_amount_1) {
		this.overdue_amount_1 = overdue_amount_1;
	}

	public String getOverdue_pay_1() {
		return overdue_pay_1;
	}

	public void setOverdue_pay_1(String overdue_pay_1) {
		this.overdue_pay_1 = overdue_pay_1;
	}

	public String getOther_fee_1() {
		return other_fee_1;
	}

	public void setOther_fee_1(String other_fee_1) {
		this.other_fee_1 = other_fee_1;
	}

	public String getIncome_amt_1() {
		return income_amt_1;
	}

	public void setIncome_amt_1(String income_amt_1) {
		this.income_amt_1 = income_amt_1;
	}

}

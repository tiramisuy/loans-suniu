package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.5 利息 (interest)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardInterest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String interest_mons_3;// 近3月有利息的月份数 3
	private String interest_mons_6;// 近6月有利息的月份数 6
	private String interest_mons_12;// 近12月有利息的月份数 12
	private String interest_mon_ratio_3;// 近3月有利息月数占比 1.00
	private String interest_mon_ratio_6;// 近6月有利息月数占比 1.00
	private String interest_mon_ratio_12;// 近12月有利息月数占比 1.00

	public String getInterest_mons_3() {
		return interest_mons_3;
	}

	public void setInterest_mons_3(String interest_mons_3) {
		this.interest_mons_3 = interest_mons_3;
	}

	public String getInterest_mons_6() {
		return interest_mons_6;
	}

	public void setInterest_mons_6(String interest_mons_6) {
		this.interest_mons_6 = interest_mons_6;
	}

	public String getInterest_mons_12() {
		return interest_mons_12;
	}

	public void setInterest_mons_12(String interest_mons_12) {
		this.interest_mons_12 = interest_mons_12;
	}

	public String getInterest_mon_ratio_3() {
		return interest_mon_ratio_3;
	}

	public void setInterest_mon_ratio_3(String interest_mon_ratio_3) {
		this.interest_mon_ratio_3 = interest_mon_ratio_3;
	}

	public String getInterest_mon_ratio_6() {
		return interest_mon_ratio_6;
	}

	public void setInterest_mon_ratio_6(String interest_mon_ratio_6) {
		this.interest_mon_ratio_6 = interest_mon_ratio_6;
	}

	public String getInterest_mon_ratio_12() {
		return interest_mon_ratio_12;
	}

	public void setInterest_mon_ratio_12(String interest_mon_ratio_12) {
		this.interest_mon_ratio_12 = interest_mon_ratio_12;
	}

}

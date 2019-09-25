package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.9 固定属性 (other_attribute)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardOtherAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String have_bill_month_num_nearly_3;// 近3月有账单的月份数 2
	private String have_bill_month_num_nearly_6;// 近6月有账单的月份数 2
	private String have_bill_month_num_nearly_12;// 近12月有账单的月份数 2
	private String longest_month_of_continuous_bill_nearly_3;// 近3月连续账单的最大月份数 2
	private String longest_month_of_continuous_bill_nearly_6;// 近6月连续账单的最大月份数 2
	private String longest_month_of_continuous_bill_nearly_12;// 近12月连续账单的最大月份数
																// 2
	private String none_bill_month_num_nearly_3;// 近3月无账单月数 1
	private String none_bill_month_num_nearly_6;// 近6月无账单月数 4
	private String none_bill_month_num_nearly_12;// 近12月无账单月数 10
	private String none_bill_month_num_per_all_3;// 近3月无账单月数/可统计的月数 0.50
	private String none_bill_month_num_per_all_6;// 近6月无账单月数/可统计的月数 2.00
	private String none_bill_month_num_per_all_12;// 近12月无账单月数/可统计的月数 5.00
	private String longest_num_of_continuous_bill_nearly_3;// 近3月单卡最长连续账单月数 2
	private String longest_num_of_continuous_bill_nearly_6;// 近6月单卡最长连续账单月数 2
	private String longest_num_of_continuous_bill_nearly_12;// 近12月单卡最长连续账单月数 2
	private String single_card_disconnect_month_num_3;// 近3月单卡最大账单断开月数 3
	private String single_card_disconnect_month_num_6;// 近6月单卡最大账单断开月数 4
	private String single_card_disconnect_month_num_12;// 近12月单卡最大账单断开月数 2

	public String getHave_bill_month_num_nearly_3() {
		return have_bill_month_num_nearly_3;
	}

	public void setHave_bill_month_num_nearly_3(String have_bill_month_num_nearly_3) {
		this.have_bill_month_num_nearly_3 = have_bill_month_num_nearly_3;
	}

	public String getHave_bill_month_num_nearly_6() {
		return have_bill_month_num_nearly_6;
	}

	public void setHave_bill_month_num_nearly_6(String have_bill_month_num_nearly_6) {
		this.have_bill_month_num_nearly_6 = have_bill_month_num_nearly_6;
	}

	public String getHave_bill_month_num_nearly_12() {
		return have_bill_month_num_nearly_12;
	}

	public void setHave_bill_month_num_nearly_12(String have_bill_month_num_nearly_12) {
		this.have_bill_month_num_nearly_12 = have_bill_month_num_nearly_12;
	}

	public String getLongest_month_of_continuous_bill_nearly_3() {
		return longest_month_of_continuous_bill_nearly_3;
	}

	public void setLongest_month_of_continuous_bill_nearly_3(String longest_month_of_continuous_bill_nearly_3) {
		this.longest_month_of_continuous_bill_nearly_3 = longest_month_of_continuous_bill_nearly_3;
	}

	public String getLongest_month_of_continuous_bill_nearly_6() {
		return longest_month_of_continuous_bill_nearly_6;
	}

	public void setLongest_month_of_continuous_bill_nearly_6(String longest_month_of_continuous_bill_nearly_6) {
		this.longest_month_of_continuous_bill_nearly_6 = longest_month_of_continuous_bill_nearly_6;
	}

	public String getLongest_month_of_continuous_bill_nearly_12() {
		return longest_month_of_continuous_bill_nearly_12;
	}

	public void setLongest_month_of_continuous_bill_nearly_12(String longest_month_of_continuous_bill_nearly_12) {
		this.longest_month_of_continuous_bill_nearly_12 = longest_month_of_continuous_bill_nearly_12;
	}

	public String getNone_bill_month_num_nearly_3() {
		return none_bill_month_num_nearly_3;
	}

	public void setNone_bill_month_num_nearly_3(String none_bill_month_num_nearly_3) {
		this.none_bill_month_num_nearly_3 = none_bill_month_num_nearly_3;
	}

	public String getNone_bill_month_num_nearly_6() {
		return none_bill_month_num_nearly_6;
	}

	public void setNone_bill_month_num_nearly_6(String none_bill_month_num_nearly_6) {
		this.none_bill_month_num_nearly_6 = none_bill_month_num_nearly_6;
	}

	public String getNone_bill_month_num_nearly_12() {
		return none_bill_month_num_nearly_12;
	}

	public void setNone_bill_month_num_nearly_12(String none_bill_month_num_nearly_12) {
		this.none_bill_month_num_nearly_12 = none_bill_month_num_nearly_12;
	}

	public String getNone_bill_month_num_per_all_3() {
		return none_bill_month_num_per_all_3;
	}

	public void setNone_bill_month_num_per_all_3(String none_bill_month_num_per_all_3) {
		this.none_bill_month_num_per_all_3 = none_bill_month_num_per_all_3;
	}

	public String getNone_bill_month_num_per_all_6() {
		return none_bill_month_num_per_all_6;
	}

	public void setNone_bill_month_num_per_all_6(String none_bill_month_num_per_all_6) {
		this.none_bill_month_num_per_all_6 = none_bill_month_num_per_all_6;
	}

	public String getNone_bill_month_num_per_all_12() {
		return none_bill_month_num_per_all_12;
	}

	public void setNone_bill_month_num_per_all_12(String none_bill_month_num_per_all_12) {
		this.none_bill_month_num_per_all_12 = none_bill_month_num_per_all_12;
	}

	public String getLongest_num_of_continuous_bill_nearly_3() {
		return longest_num_of_continuous_bill_nearly_3;
	}

	public void setLongest_num_of_continuous_bill_nearly_3(String longest_num_of_continuous_bill_nearly_3) {
		this.longest_num_of_continuous_bill_nearly_3 = longest_num_of_continuous_bill_nearly_3;
	}

	public String getLongest_num_of_continuous_bill_nearly_6() {
		return longest_num_of_continuous_bill_nearly_6;
	}

	public void setLongest_num_of_continuous_bill_nearly_6(String longest_num_of_continuous_bill_nearly_6) {
		this.longest_num_of_continuous_bill_nearly_6 = longest_num_of_continuous_bill_nearly_6;
	}

	public String getLongest_num_of_continuous_bill_nearly_12() {
		return longest_num_of_continuous_bill_nearly_12;
	}

	public void setLongest_num_of_continuous_bill_nearly_12(String longest_num_of_continuous_bill_nearly_12) {
		this.longest_num_of_continuous_bill_nearly_12 = longest_num_of_continuous_bill_nearly_12;
	}

	public String getSingle_card_disconnect_month_num_3() {
		return single_card_disconnect_month_num_3;
	}

	public void setSingle_card_disconnect_month_num_3(String single_card_disconnect_month_num_3) {
		this.single_card_disconnect_month_num_3 = single_card_disconnect_month_num_3;
	}

	public String getSingle_card_disconnect_month_num_6() {
		return single_card_disconnect_month_num_6;
	}

	public void setSingle_card_disconnect_month_num_6(String single_card_disconnect_month_num_6) {
		this.single_card_disconnect_month_num_6 = single_card_disconnect_month_num_6;
	}

	public String getSingle_card_disconnect_month_num_12() {
		return single_card_disconnect_month_num_12;
	}

	public void setSingle_card_disconnect_month_num_12(String single_card_disconnect_month_num_12) {
		this.single_card_disconnect_month_num_12 = single_card_disconnect_month_num_12;
	}

}

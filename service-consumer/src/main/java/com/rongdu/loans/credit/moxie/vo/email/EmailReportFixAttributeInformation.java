package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 固定属性
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportFixAttributeInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String months_have_bill_l3m;// 近3月有账单的月数 3
	private String months_have_bill_l6m;// 近6月有账单的月数 4
	private String months_have_bill_l12m;// 近12月有账单的月数 4

	private String months_without_bill_l3m;// 近3月无账单的月数 2
	private String months_without_bill_l6m;// 近6月无账单的月数 3
	private String months_without_bill_l12m;// 近12月无账单的月数 4

	private String max_months_have_succession_bills_l3m;// 近3月连续账单最长月数 3
	private String max_months_have_succession_bills_l6m;// 近6月连续账单最长月数 4
	private String max_months_have_succession_bills_l12m;// 近12月连续账单最长月数 4

	private String max_months_single_card_succession_bills_l3m;// 近3月单卡最长连续账单期数
	private String max_months_single_card_succession_bills_l6m;// 近6月单卡最长连续账单期数
	private String max_months_single_card_succession_bill_l12m;// 近12月单卡最长连续账单期数

	private String max_months_single_card_break_l6m;// 近6月单卡最大账单断开期数 5
	private String max_months_single_card_break_l12m;// 近12月单卡最大账单断开期数 6
	private String max_months_single_card_break_l3m;// 近3月单卡最大账单断开期数 2

	private String months_without_bills_l3m_div_all_months_have_bill;// 近3月无账单的月数/可统计的月数
	private String months_without_bills_l6m_div_all_months_have_bill;// 近6月无账单的月数/可统计的月数
	private String months_without_bills_l12m_div_all_months_have_bill;// 近12月无账单的月数/可统计的月数

	public String getMonths_have_bill_l3m() {
		return months_have_bill_l3m;
	}

	public void setMonths_have_bill_l3m(String months_have_bill_l3m) {
		this.months_have_bill_l3m = months_have_bill_l3m;
	}

	public String getMonths_have_bill_l6m() {
		return months_have_bill_l6m;
	}

	public void setMonths_have_bill_l6m(String months_have_bill_l6m) {
		this.months_have_bill_l6m = months_have_bill_l6m;
	}

	public String getMonths_have_bill_l12m() {
		return months_have_bill_l12m;
	}

	public void setMonths_have_bill_l12m(String months_have_bill_l12m) {
		this.months_have_bill_l12m = months_have_bill_l12m;
	}

	public String getMax_months_have_succession_bills_l3m() {
		return max_months_have_succession_bills_l3m;
	}

	public void setMax_months_have_succession_bills_l3m(String max_months_have_succession_bills_l3m) {
		this.max_months_have_succession_bills_l3m = max_months_have_succession_bills_l3m;
	}

	public String getMax_months_have_succession_bills_l6m() {
		return max_months_have_succession_bills_l6m;
	}

	public void setMax_months_have_succession_bills_l6m(String max_months_have_succession_bills_l6m) {
		this.max_months_have_succession_bills_l6m = max_months_have_succession_bills_l6m;
	}

	public String getMax_months_have_succession_bills_l12m() {
		return max_months_have_succession_bills_l12m;
	}

	public void setMax_months_have_succession_bills_l12m(String max_months_have_succession_bills_l12m) {
		this.max_months_have_succession_bills_l12m = max_months_have_succession_bills_l12m;
	}

	public String getMax_months_single_card_succession_bills_l6m() {
		return max_months_single_card_succession_bills_l6m;
	}

	public void setMax_months_single_card_succession_bills_l6m(String max_months_single_card_succession_bills_l6m) {
		this.max_months_single_card_succession_bills_l6m = max_months_single_card_succession_bills_l6m;
	}

	public String getMonths_without_bill_l6m() {
		return months_without_bill_l6m;
	}

	public void setMonths_without_bill_l6m(String months_without_bill_l6m) {
		this.months_without_bill_l6m = months_without_bill_l6m;
	}

	public String getMonths_without_bill_l12m() {
		return months_without_bill_l12m;
	}

	public void setMonths_without_bill_l12m(String months_without_bill_l12m) {
		this.months_without_bill_l12m = months_without_bill_l12m;
	}

	public String getMax_months_single_card_succession_bill_l12m() {
		return max_months_single_card_succession_bill_l12m;
	}

	public void setMax_months_single_card_succession_bill_l12m(String max_months_single_card_succession_bill_l12m) {
		this.max_months_single_card_succession_bill_l12m = max_months_single_card_succession_bill_l12m;
	}

	public String getMonths_without_bills_l6m_div_all_months_have_bill() {
		return months_without_bills_l6m_div_all_months_have_bill;
	}

	public void setMonths_without_bills_l6m_div_all_months_have_bill(
			String months_without_bills_l6m_div_all_months_have_bill) {
		this.months_without_bills_l6m_div_all_months_have_bill = months_without_bills_l6m_div_all_months_have_bill;
	}

	public String getMax_months_single_card_break_l6m() {
		return max_months_single_card_break_l6m;
	}

	public void setMax_months_single_card_break_l6m(String max_months_single_card_break_l6m) {
		this.max_months_single_card_break_l6m = max_months_single_card_break_l6m;
	}

	public String getMonths_without_bills_l12m_div_all_months_have_bill() {
		return months_without_bills_l12m_div_all_months_have_bill;
	}

	public void setMonths_without_bills_l12m_div_all_months_have_bill(
			String months_without_bills_l12m_div_all_months_have_bill) {
		this.months_without_bills_l12m_div_all_months_have_bill = months_without_bills_l12m_div_all_months_have_bill;
	}

	public String getMax_months_single_card_break_l12m() {
		return max_months_single_card_break_l12m;
	}

	public void setMax_months_single_card_break_l12m(String max_months_single_card_break_l12m) {
		this.max_months_single_card_break_l12m = max_months_single_card_break_l12m;
	}

	public String getMonths_without_bill_l3m() {
		return months_without_bill_l3m;
	}

	public void setMonths_without_bill_l3m(String months_without_bill_l3m) {
		this.months_without_bill_l3m = months_without_bill_l3m;
	}

	public String getMonths_without_bills_l3m_div_all_months_have_bill() {
		return months_without_bills_l3m_div_all_months_have_bill;
	}

	public void setMonths_without_bills_l3m_div_all_months_have_bill(
			String months_without_bills_l3m_div_all_months_have_bill) {
		this.months_without_bills_l3m_div_all_months_have_bill = months_without_bills_l3m_div_all_months_have_bill;
	}

	public String getMax_months_single_card_succession_bills_l3m() {
		return max_months_single_card_succession_bills_l3m;
	}

	public void setMax_months_single_card_succession_bills_l3m(String max_months_single_card_succession_bills_l3m) {
		this.max_months_single_card_succession_bills_l3m = max_months_single_card_succession_bills_l3m;
	}

	public String getMax_months_single_card_break_l3m() {
		return max_months_single_card_break_l3m;
	}

	public void setMax_months_single_card_break_l3m(String max_months_single_card_break_l3m) {
		this.max_months_single_card_break_l3m = max_months_single_card_break_l3m;
	}

}

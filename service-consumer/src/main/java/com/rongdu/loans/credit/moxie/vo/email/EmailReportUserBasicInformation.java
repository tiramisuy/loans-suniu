package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 用户基本信息
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportUserBasicInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 姓名
	private String name;
	// 邮箱
	private String email;
	// 活跃卡数
	private String active_cards;
	// 银行数
	private String bank_nums;
	// 最初账单日
	private String first_bill_date;
	// 最早一期账单距今月份数
	private String first_bill_months_from_now;
	// 客户族群标志
	private String customer_group_tag;
	// 客户套现标志
	private String cash_out_tag;
	// 账单最新认证时间（最新一期账单日期）
	private String latest_authentication_time;
	// 近6月本人一手账单占所有账单比例
	private String ratio_original_bill_div_all_bills_l6m;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActive_cards() {
		return active_cards;
	}

	public void setActive_cards(String active_cards) {
		this.active_cards = active_cards;
	}

	public String getBank_nums() {
		return bank_nums;
	}

	public void setBank_nums(String bank_nums) {
		this.bank_nums = bank_nums;
	}

	public String getFirst_bill_date() {
		return first_bill_date;
	}

	public void setFirst_bill_date(String first_bill_date) {
		this.first_bill_date = first_bill_date;
	}

	public String getFirst_bill_months_from_now() {
		return first_bill_months_from_now;
	}

	public void setFirst_bill_months_from_now(String first_bill_months_from_now) {
		this.first_bill_months_from_now = first_bill_months_from_now;
	}

	public String getCustomer_group_tag() {
		return customer_group_tag;
	}

	public void setCustomer_group_tag(String customer_group_tag) {
		this.customer_group_tag = customer_group_tag;
	}

	public String getCash_out_tag() {
		return cash_out_tag;
	}

	public void setCash_out_tag(String cash_out_tag) {
		this.cash_out_tag = cash_out_tag;
	}

	public String getLatest_authentication_time() {
		return latest_authentication_time;
	}

	public void setLatest_authentication_time(String latest_authentication_time) {
		this.latest_authentication_time = latest_authentication_time;
	}

	public String getRatio_original_bill_div_all_bills_l6m() {
		return ratio_original_bill_div_all_bills_l6m;
	}

	public void setRatio_original_bill_div_all_bills_l6m(String ratio_original_bill_div_all_bills_l6m) {
		this.ratio_original_bill_div_all_bills_l6m = ratio_original_bill_div_all_bills_l6m;
	}

}

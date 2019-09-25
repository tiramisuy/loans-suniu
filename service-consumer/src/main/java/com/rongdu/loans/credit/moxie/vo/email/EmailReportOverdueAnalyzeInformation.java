package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 逾期
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportOverdueAnalyzeInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String months_not_overdue_l3m;// 近3月非延滞期数 3
	private String months_not_overdue_l6m;// 近6月非延滞期数 6
	private String months_not_overdue_l12m;// 近12月非延滞期数 12

	private String first_overdue_amount_l3m;// 近3月首次延滞金额 52.10
	private String first_overdue_amount_l6m;// 近6月首次延滞金额 52.10
	private String first_overdue_amount_l12m;// 近12月首次延滞金额 52.10

	private String max_excess_and_overdue_fine_amount;// 近3月最高（超额金+滞纳金）金额 52.1
	private String max_excess_and_overdue_fine_amount_l6m;// 近6月最高（超额金+滞纳金）金额
	private String max_excess_and_overdue_fine_amount_l12m;// 近12月最高（超额金+滞纳金）金额

	private String max_overdue_fine_l3m;// 近3月最高延滞金额 52.1
	private String max_overdue_fine_l6m;// 近6月最高延滞金额 52.1
	private String max_overdue_fine_l12m;// 近12月最高延滞金额 52.1

	private String months_last_overdue_months_from_now_l3m;// 近3月最近产生延滞金额距今的月数 2
	private String months_last_overdue_months_from_now_l6m;// 近6月最近产生延滞金额距今的月数 2
	private String months_last_overdue_months_from_now_l12m;// 近12月最近产生延滞金额距今的月数

	private String months_overdue_1_months_l3m;// 近3期逾期期数为1的月份数 2
	private String months_overdue_1_months_l6m;// 近6期逾期期数为1的月份数 3
	private String months_overdue_1_months_l12m;// 近12期逾期期数为1的月份数 3

	private String max_overdue_status_l6m;// 近6月最高延滞状态 3
	private String max_overdue_status_l12m;// 近12月最高延滞状态 3
	private String max_overdue_status_l3m;// 近3月最高延滞状态 3

	private String months_ovderdue_l3m;// 近3月延滞期数 2
	private String months_ovderdue_l6m;// 近6月延滞期数 2
	private String months_ovderdue_l12m;// 近12月延滞期数 2

	private String banks_withdraw_overdue_fine_l6m;// 近6月包含延滞金银行机构数 2
	private String banks_withdraw_overdue_fine_l12m;// 近12月包含延滞金银行机构数 2
	private String banks_withdraw_overdue_fine_l3m;// 近3月包含延滞金银行机构数 2

	private String card_nums_with_overdue_fine_l3m;// 近3月包含延滞金卡片数 2
	private String card_nums_with_overdue_fine_l6m;// 近6月包含延滞金卡片数 2
	private String card_nums_with_overdue_fine_l12m;// 近12月包含延滞金卡片数 2

	public String getMonths_not_overdue_l3m() {
		return months_not_overdue_l3m;
	}

	public void setMonths_not_overdue_l3m(String months_not_overdue_l3m) {
		this.months_not_overdue_l3m = months_not_overdue_l3m;
	}

	public String getMonths_not_overdue_l6m() {
		return months_not_overdue_l6m;
	}

	public void setMonths_not_overdue_l6m(String months_not_overdue_l6m) {
		this.months_not_overdue_l6m = months_not_overdue_l6m;
	}

	public String getMonths_not_overdue_l12m() {
		return months_not_overdue_l12m;
	}

	public void setMonths_not_overdue_l12m(String months_not_overdue_l12m) {
		this.months_not_overdue_l12m = months_not_overdue_l12m;
	}

	public String getFirst_overdue_amount_l3m() {
		return first_overdue_amount_l3m;
	}

	public void setFirst_overdue_amount_l3m(String first_overdue_amount_l3m) {
		this.first_overdue_amount_l3m = first_overdue_amount_l3m;
	}

	public String getFirst_overdue_amount_l6m() {
		return first_overdue_amount_l6m;
	}

	public void setFirst_overdue_amount_l6m(String first_overdue_amount_l6m) {
		this.first_overdue_amount_l6m = first_overdue_amount_l6m;
	}

	public String getMax_excess_and_overdue_fine_amount() {
		return max_excess_and_overdue_fine_amount;
	}

	public void setMax_excess_and_overdue_fine_amount(String max_excess_and_overdue_fine_amount) {
		this.max_excess_and_overdue_fine_amount = max_excess_and_overdue_fine_amount;
	}

	public String getFirst_overdue_amount_l12m() {
		return first_overdue_amount_l12m;
	}

	public void setFirst_overdue_amount_l12m(String first_overdue_amount_l12m) {
		this.first_overdue_amount_l12m = first_overdue_amount_l12m;
	}

	public String getMax_excess_and_overdue_fine_amount_l6m() {
		return max_excess_and_overdue_fine_amount_l6m;
	}

	public void setMax_excess_and_overdue_fine_amount_l6m(String max_excess_and_overdue_fine_amount_l6m) {
		this.max_excess_and_overdue_fine_amount_l6m = max_excess_and_overdue_fine_amount_l6m;
	}

	public String getMax_excess_and_overdue_fine_amount_l12m() {
		return max_excess_and_overdue_fine_amount_l12m;
	}

	public void setMax_excess_and_overdue_fine_amount_l12m(String max_excess_and_overdue_fine_amount_l12m) {
		this.max_excess_and_overdue_fine_amount_l12m = max_excess_and_overdue_fine_amount_l12m;
	}

	public String getMax_overdue_fine_l3m() {
		return max_overdue_fine_l3m;
	}

	public void setMax_overdue_fine_l3m(String max_overdue_fine_l3m) {
		this.max_overdue_fine_l3m = max_overdue_fine_l3m;
	}

	public String getMax_overdue_fine_l6m() {
		return max_overdue_fine_l6m;
	}

	public void setMax_overdue_fine_l6m(String max_overdue_fine_l6m) {
		this.max_overdue_fine_l6m = max_overdue_fine_l6m;
	}

	public String getMax_overdue_fine_l12m() {
		return max_overdue_fine_l12m;
	}

	public void setMax_overdue_fine_l12m(String max_overdue_fine_l12m) {
		this.max_overdue_fine_l12m = max_overdue_fine_l12m;
	}

	public String getMonths_last_overdue_months_from_now_l3m() {
		return months_last_overdue_months_from_now_l3m;
	}

	public void setMonths_last_overdue_months_from_now_l3m(String months_last_overdue_months_from_now_l3m) {
		this.months_last_overdue_months_from_now_l3m = months_last_overdue_months_from_now_l3m;
	}

	public String getMonths_last_overdue_months_from_now_l6m() {
		return months_last_overdue_months_from_now_l6m;
	}

	public void setMonths_last_overdue_months_from_now_l6m(String months_last_overdue_months_from_now_l6m) {
		this.months_last_overdue_months_from_now_l6m = months_last_overdue_months_from_now_l6m;
	}

	public String getMonths_last_overdue_months_from_now_l12m() {
		return months_last_overdue_months_from_now_l12m;
	}

	public void setMonths_last_overdue_months_from_now_l12m(String months_last_overdue_months_from_now_l12m) {
		this.months_last_overdue_months_from_now_l12m = months_last_overdue_months_from_now_l12m;
	}

	public String getMonths_overdue_1_months_l3m() {
		return months_overdue_1_months_l3m;
	}

	public void setMonths_overdue_1_months_l3m(String months_overdue_1_months_l3m) {
		this.months_overdue_1_months_l3m = months_overdue_1_months_l3m;
	}

	public String getMonths_overdue_1_months_l6m() {
		return months_overdue_1_months_l6m;
	}

	public void setMonths_overdue_1_months_l6m(String months_overdue_1_months_l6m) {
		this.months_overdue_1_months_l6m = months_overdue_1_months_l6m;
	}

	public String getMonths_overdue_1_months_l12m() {
		return months_overdue_1_months_l12m;
	}

	public void setMonths_overdue_1_months_l12m(String months_overdue_1_months_l12m) {
		this.months_overdue_1_months_l12m = months_overdue_1_months_l12m;
	}

	public String getMax_overdue_status_l6m() {
		return max_overdue_status_l6m;
	}

	public void setMax_overdue_status_l6m(String max_overdue_status_l6m) {
		this.max_overdue_status_l6m = max_overdue_status_l6m;
	}

	public String getMax_overdue_status_l12m() {
		return max_overdue_status_l12m;
	}

	public void setMax_overdue_status_l12m(String max_overdue_status_l12m) {
		this.max_overdue_status_l12m = max_overdue_status_l12m;
	}

	public String getMax_overdue_status_l3m() {
		return max_overdue_status_l3m;
	}

	public void setMax_overdue_status_l3m(String max_overdue_status_l3m) {
		this.max_overdue_status_l3m = max_overdue_status_l3m;
	}

	public String getMonths_ovderdue_l3m() {
		return months_ovderdue_l3m;
	}

	public void setMonths_ovderdue_l3m(String months_ovderdue_l3m) {
		this.months_ovderdue_l3m = months_ovderdue_l3m;
	}

	public String getMonths_ovderdue_l6m() {
		return months_ovderdue_l6m;
	}

	public void setMonths_ovderdue_l6m(String months_ovderdue_l6m) {
		this.months_ovderdue_l6m = months_ovderdue_l6m;
	}

	public String getMonths_ovderdue_l12m() {
		return months_ovderdue_l12m;
	}

	public void setMonths_ovderdue_l12m(String months_ovderdue_l12m) {
		this.months_ovderdue_l12m = months_ovderdue_l12m;
	}

	public String getBanks_withdraw_overdue_fine_l6m() {
		return banks_withdraw_overdue_fine_l6m;
	}

	public void setBanks_withdraw_overdue_fine_l6m(String banks_withdraw_overdue_fine_l6m) {
		this.banks_withdraw_overdue_fine_l6m = banks_withdraw_overdue_fine_l6m;
	}

	public String getBanks_withdraw_overdue_fine_l12m() {
		return banks_withdraw_overdue_fine_l12m;
	}

	public void setBanks_withdraw_overdue_fine_l12m(String banks_withdraw_overdue_fine_l12m) {
		this.banks_withdraw_overdue_fine_l12m = banks_withdraw_overdue_fine_l12m;
	}

	public String getCard_nums_with_overdue_fine_l6m() {
		return card_nums_with_overdue_fine_l6m;
	}

	public void setCard_nums_with_overdue_fine_l6m(String card_nums_with_overdue_fine_l6m) {
		this.card_nums_with_overdue_fine_l6m = card_nums_with_overdue_fine_l6m;
	}

	public String getCard_nums_with_overdue_fine_l12m() {
		return card_nums_with_overdue_fine_l12m;
	}

	public void setCard_nums_with_overdue_fine_l12m(String card_nums_with_overdue_fine_l12m) {
		this.card_nums_with_overdue_fine_l12m = card_nums_with_overdue_fine_l12m;
	}

	public String getBanks_withdraw_overdue_fine_l3m() {
		return banks_withdraw_overdue_fine_l3m;
	}

	public void setBanks_withdraw_overdue_fine_l3m(String banks_withdraw_overdue_fine_l3m) {
		this.banks_withdraw_overdue_fine_l3m = banks_withdraw_overdue_fine_l3m;
	}

	public String getCard_nums_with_overdue_fine_l3m() {
		return card_nums_with_overdue_fine_l3m;
	}

	public void setCard_nums_with_overdue_fine_l3m(String card_nums_with_overdue_fine_l3m) {
		this.card_nums_with_overdue_fine_l3m = card_nums_with_overdue_fine_l3m;
	}

}

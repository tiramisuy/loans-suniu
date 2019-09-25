package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.8 逾期 (overdue_creditcard)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardOverdueCreditcard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String non_delayed_periods_num_3;// 近3月非延滞期数 3
	private String non_delayed_periods_num_6;// 近6月非延滞期数 6
	private String non_delayed_periods_num_12;// 近12月非延滞期数 12
	private String delayed_periods_num_3;// 近3月延滞期数 3
	private String delayed_periods_num_6;// 近6月延滞期数 6
	private String delayed_periods_num_12;// 近12月延滞期数 12
	private String delayed_bank_num_3;// 近3月延滞金银行机构数 0
	private String delayed_bank_num_6;// 近6月延滞金银行机构数 1
	private String delayed_bank_num_12;// 近12月延滞金银行机构数 1
	private String delayed_card_num_3;// 近3月延滞金卡片数 1
	private String delayed_card_num_6;// 近6月延滞金卡片数 1
	private String delayed_card_num_12;// 近12月延滞金卡片数 1
	private String delayed_amnt_first_3;// 近3月首次延滞金额（元） 124.24
	private String delayed_amnt_first_6;// 近6月首次延滞金额（元） 124.24
	private String delayed_amnt_first_12;// 近12月首次延滞金额（元） 124.24
	private String max_amnt_of_beyond_delayed_3;// 近3月最高（超额金+延滞金）金额（元） 124.24
	private String max_amnt_of_beyond_delayed_6;// 近6月最高（超额金+延滞金）金额（元） 124.24
	private String max_amnt_of_beyond_delayed_12;// 近12月最高（超额金+延滞金）金额（元） 124.24
	private String max_beyond_amnt_3;// 近3月最高延滞金额（元） 10.00
	private String max_beyond_amnt_6;// 近6月最高延滞金额（元） 10.00
	private String max_beyond_amnt_12;// 近12月最高延滞金额（元） 10.00
	private String highest_delayed_3;// 近3月最高延滞状态 2
	private String highest_delayed_6;// 近6月最高延滞状态 3
	private String highest_delayed_12;// 近12月最高延滞状态 3
	private String last_delayed_mon_num_3;// 近3月最近产生延滞金额距今的月份数 4
	private String last_delayed_mon_num_6;// 近6月最近产生延滞金额距今的月份数 4
	private String last_delayed_mon_num_12;// 近12月最近产生延滞金额距今的月份数 4
	private String case_delayed_period_equals_one_mon_num_3;// 近3月逾期期数为1的月份数 1
	private String case_delayed_period_equals_one_mon_num_6;// 近6月逾期期数为1的月份数 1
	private String case_delayed_period_equals_one_mon_num_12;// 近12月逾期期数为1的月份数 1

	public String getNon_delayed_periods_num_3() {
		return non_delayed_periods_num_3;
	}

	public void setNon_delayed_periods_num_3(String non_delayed_periods_num_3) {
		this.non_delayed_periods_num_3 = non_delayed_periods_num_3;
	}

	public String getNon_delayed_periods_num_6() {
		return non_delayed_periods_num_6;
	}

	public void setNon_delayed_periods_num_6(String non_delayed_periods_num_6) {
		this.non_delayed_periods_num_6 = non_delayed_periods_num_6;
	}

	public String getNon_delayed_periods_num_12() {
		return non_delayed_periods_num_12;
	}

	public void setNon_delayed_periods_num_12(String non_delayed_periods_num_12) {
		this.non_delayed_periods_num_12 = non_delayed_periods_num_12;
	}

	public String getDelayed_periods_num_3() {
		return delayed_periods_num_3;
	}

	public void setDelayed_periods_num_3(String delayed_periods_num_3) {
		this.delayed_periods_num_3 = delayed_periods_num_3;
	}

	public String getDelayed_periods_num_6() {
		return delayed_periods_num_6;
	}

	public void setDelayed_periods_num_6(String delayed_periods_num_6) {
		this.delayed_periods_num_6 = delayed_periods_num_6;
	}

	public String getDelayed_periods_num_12() {
		return delayed_periods_num_12;
	}

	public void setDelayed_periods_num_12(String delayed_periods_num_12) {
		this.delayed_periods_num_12 = delayed_periods_num_12;
	}

	public String getDelayed_bank_num_3() {
		return delayed_bank_num_3;
	}

	public void setDelayed_bank_num_3(String delayed_bank_num_3) {
		this.delayed_bank_num_3 = delayed_bank_num_3;
	}

	public String getDelayed_bank_num_6() {
		return delayed_bank_num_6;
	}

	public void setDelayed_bank_num_6(String delayed_bank_num_6) {
		this.delayed_bank_num_6 = delayed_bank_num_6;
	}

	public String getDelayed_bank_num_12() {
		return delayed_bank_num_12;
	}

	public void setDelayed_bank_num_12(String delayed_bank_num_12) {
		this.delayed_bank_num_12 = delayed_bank_num_12;
	}

	public String getDelayed_card_num_3() {
		return delayed_card_num_3;
	}

	public void setDelayed_card_num_3(String delayed_card_num_3) {
		this.delayed_card_num_3 = delayed_card_num_3;
	}

	public String getDelayed_card_num_6() {
		return delayed_card_num_6;
	}

	public void setDelayed_card_num_6(String delayed_card_num_6) {
		this.delayed_card_num_6 = delayed_card_num_6;
	}

	public String getDelayed_card_num_12() {
		return delayed_card_num_12;
	}

	public void setDelayed_card_num_12(String delayed_card_num_12) {
		this.delayed_card_num_12 = delayed_card_num_12;
	}

	public String getDelayed_amnt_first_3() {
		return delayed_amnt_first_3;
	}

	public void setDelayed_amnt_first_3(String delayed_amnt_first_3) {
		this.delayed_amnt_first_3 = delayed_amnt_first_3;
	}

	public String getDelayed_amnt_first_6() {
		return delayed_amnt_first_6;
	}

	public void setDelayed_amnt_first_6(String delayed_amnt_first_6) {
		this.delayed_amnt_first_6 = delayed_amnt_first_6;
	}

	public String getDelayed_amnt_first_12() {
		return delayed_amnt_first_12;
	}

	public void setDelayed_amnt_first_12(String delayed_amnt_first_12) {
		this.delayed_amnt_first_12 = delayed_amnt_first_12;
	}

	public String getMax_amnt_of_beyond_delayed_3() {
		return max_amnt_of_beyond_delayed_3;
	}

	public void setMax_amnt_of_beyond_delayed_3(String max_amnt_of_beyond_delayed_3) {
		this.max_amnt_of_beyond_delayed_3 = max_amnt_of_beyond_delayed_3;
	}

	public String getMax_amnt_of_beyond_delayed_6() {
		return max_amnt_of_beyond_delayed_6;
	}

	public void setMax_amnt_of_beyond_delayed_6(String max_amnt_of_beyond_delayed_6) {
		this.max_amnt_of_beyond_delayed_6 = max_amnt_of_beyond_delayed_6;
	}

	public String getMax_amnt_of_beyond_delayed_12() {
		return max_amnt_of_beyond_delayed_12;
	}

	public void setMax_amnt_of_beyond_delayed_12(String max_amnt_of_beyond_delayed_12) {
		this.max_amnt_of_beyond_delayed_12 = max_amnt_of_beyond_delayed_12;
	}

	public String getMax_beyond_amnt_3() {
		return max_beyond_amnt_3;
	}

	public void setMax_beyond_amnt_3(String max_beyond_amnt_3) {
		this.max_beyond_amnt_3 = max_beyond_amnt_3;
	}

	public String getMax_beyond_amnt_6() {
		return max_beyond_amnt_6;
	}

	public void setMax_beyond_amnt_6(String max_beyond_amnt_6) {
		this.max_beyond_amnt_6 = max_beyond_amnt_6;
	}

	public String getMax_beyond_amnt_12() {
		return max_beyond_amnt_12;
	}

	public void setMax_beyond_amnt_12(String max_beyond_amnt_12) {
		this.max_beyond_amnt_12 = max_beyond_amnt_12;
	}

	public String getHighest_delayed_3() {
		return highest_delayed_3;
	}

	public void setHighest_delayed_3(String highest_delayed_3) {
		this.highest_delayed_3 = highest_delayed_3;
	}

	public String getHighest_delayed_6() {
		return highest_delayed_6;
	}

	public void setHighest_delayed_6(String highest_delayed_6) {
		this.highest_delayed_6 = highest_delayed_6;
	}

	public String getHighest_delayed_12() {
		return highest_delayed_12;
	}

	public void setHighest_delayed_12(String highest_delayed_12) {
		this.highest_delayed_12 = highest_delayed_12;
	}

	public String getLast_delayed_mon_num_3() {
		return last_delayed_mon_num_3;
	}

	public void setLast_delayed_mon_num_3(String last_delayed_mon_num_3) {
		this.last_delayed_mon_num_3 = last_delayed_mon_num_3;
	}

	public String getLast_delayed_mon_num_6() {
		return last_delayed_mon_num_6;
	}

	public void setLast_delayed_mon_num_6(String last_delayed_mon_num_6) {
		this.last_delayed_mon_num_6 = last_delayed_mon_num_6;
	}

	public String getLast_delayed_mon_num_12() {
		return last_delayed_mon_num_12;
	}

	public void setLast_delayed_mon_num_12(String last_delayed_mon_num_12) {
		this.last_delayed_mon_num_12 = last_delayed_mon_num_12;
	}

	public String getCase_delayed_period_equals_one_mon_num_3() {
		return case_delayed_period_equals_one_mon_num_3;
	}

	public void setCase_delayed_period_equals_one_mon_num_3(String case_delayed_period_equals_one_mon_num_3) {
		this.case_delayed_period_equals_one_mon_num_3 = case_delayed_period_equals_one_mon_num_3;
	}

	public String getCase_delayed_period_equals_one_mon_num_6() {
		return case_delayed_period_equals_one_mon_num_6;
	}

	public void setCase_delayed_period_equals_one_mon_num_6(String case_delayed_period_equals_one_mon_num_6) {
		this.case_delayed_period_equals_one_mon_num_6 = case_delayed_period_equals_one_mon_num_6;
	}

	public String getCase_delayed_period_equals_one_mon_num_12() {
		return case_delayed_period_equals_one_mon_num_12;
	}

	public void setCase_delayed_period_equals_one_mon_num_12(String case_delayed_period_equals_one_mon_num_12) {
		this.case_delayed_period_equals_one_mon_num_12 = case_delayed_period_equals_one_mon_num_12;
	}

}

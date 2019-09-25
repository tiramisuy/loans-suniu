package com.rongdu.loans.credit.moxie.vo.bank.debitcard;

import java.io.Serializable;

/**
 * 2.1 借记卡 （debitcard_detailses）
 * 
 * @author liuzhuang
 * 
 */
public class DebitcardDebitcardDetailsesVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String debitcard_income_3;// 近3月收入金额（元） 150235.95
	private String debitCard_Income_6;// 近6月收入金额（元） 259105.57
	private String debitCard_Income_12;// 近12月收入金额（元） 356173.22
	private String debitcard_income_6_avg;// 近6月月均收入金额（元） 93184.26
	private String debitcard_income_12_avg;// 近12月月均收入金额（元） 196347.77
	private String salary_income_amount_3m;// 近3月工资收入金额（元） 125364.90
	private String salary_income_amount_6m;// 近6月工资收入金额（元） 231534.45
	private String salary_income_amount_12m;// 近12月工资收入金额（元） 184923.45
	private String salary_income_amount_average_6m;// 近6月月均工资收入金额（元） 139845.90
	private String salary_income_amount_average_12m;// 近12月月均工资收入金额（元） 121523.76
	private String salary_income_month_num_3;// 近3月有工资收入月数 3
	private String salary_income_month_num_6;// 近6月有工资收入月数 6
	private String salary_income_month_num_12;// 近12月有工资收入月数 12
	private String loan_in_amount_3;// 近3月贷款收入金额 25213.89
	private String loan_in_amount_6;// 近6月贷款收入金额 45223.89
	private String loan_in_amount_12;// 近12月贷款收入金额 85143.89
	private String loan_in_amount_avg_6;// 近6月月均贷款收入金额 125243.89
	private String loan_in_amount_avg_12;// 近12月月均贷款收入金额 225243.89
	private String loan_in_month_num_3;// 近3月有贷款收入月数 3
	private String loan_in_month_num_6;// 近6月有贷款收入月数 6
	private String loan_in_month_num_12;// 近12月有贷款收入月数 12
	private String debitcard_outcome_3;// 近3月支出金额（元） 21243.89
	private String debitcard_outcome_6;// 近6月支出金额（元） 35243.89
	private String debitcard_outcome_12;// 近12月支出金额（元） 89243.89
	private String debitcard_outcome_6_avg;// 近6月月均支出金额（元） 25243.89
	private String debitcard_outcome_12_avg;// 近12月月均支出金额（元） 55243.89
	private String repany_loan_amount_3;// 近3月贷款还款金额（元） 25243.89
	private String repany_loan_amount_6;// 近6月贷款还款金额（元） 32243.89
	private String repany_loan_amount_12;// 近12月贷款还款金额（元） 625243.89
	private String repany_loan_amount_avg_6;// 近6月月均贷款还款金额（元） 15213.89
	private String repany_loan_amount_avg_12;// 近12月月均贷款还款金额（元） 122343.89
	private String repany_loan_mon_num_3;// 近3月有贷款还款月数 3
	private String repany_loan_mon_num_6;// 近6月有贷款还款月数 6
	private String repany_loan_mon_num_12;// 近12月有贷款还款月数 12
	private String debitcard_in_num_trans_num_ratio_3;// 近3月无收入的月数/有流水的月数 2.00
	private String debitcard_in_num_trans_num_ratio_6;// 近6月无收入的月数/有流水的月数 0.50
	private String debitcard_in_num_trans_num_ratio_12;// 近12月无收入的月数/有流水的月数 0.20
	private String debitcard_max_balance_3;// 近3月最大余额（元） 50955.98
	private String debitcard_max_balance_6;// 近6月最大余额（元） 205069.55
	private String debitcard_max_balance_12;// 近12月最大余额（元） 205069.55
	private String debitcard_recently_balance_3;// 近3月最近余额（元） 1179.05
	private String debitcard_recently_balance_6;// 近6月最近余额（元） 1179.05
	private String debitcard_recently_balance_12;// 近12月最近余额（元） 1179.05
	private String debitcard_consume_amount_3;// 近3月消费金额（元） 5313.00
	private String debitcard_consume_amount_6;// 近6月消费金额（元） 7261.00
	private String debitcard_consume_amount_12;// 近12月消费金额（元） 12849.00
	private String debitcard_consume_amount_6_avg;// 近6月月均消费金额（元） 3910.00
	private String debitcard_consume_amount_12_avg;// 近12月月均消费金额（元） 24908.00
	private String debitcard_consume_count_3;// 近3月消费笔数 3
	private String debitcard_consume_count_6;// 近6月消费笔数 6
	private String debitcard_consume_count_12;// 近12月消费笔数 12
	private String debitcard_consume_count_6_avg;// 近6月月均消费笔数 6
	private String debitcard_consume_count_12_avg;// 近12月月均消费笔数 12
	private String debitcard_max_continue_consume_month_3;// 近3月最大连续消费月数 2
	private String debitcard_max_continue_consume_month_6;// 近6月最大连续消费月数 6
	private String debitcard_max_continue_consume_month_12;// 近12月最大连续消费月数 12
	private String debitcard_withdraw_amount_3;// 近3月取现金额（元） 2000.00
	private String debitcard_withdraw_amount_6;// 近6月取现金额（元） 3900.00
	private String debitcard_withdraw_amount_12;// 近12月取现金额（元） 14900.00
	private String debitcard_withdraw_amount_6_avg;// 近6月月均取现金额（元） 650.00
	private String debitcard_withdraw_amount_12_avg;// 近12月月均取现金额（元） 1241.67
	private String debitcard_withdraw_count_3;// 近3月取现笔数 1
	private String debitcard_withdraw_count_6;// 近6月取现笔数 4
	private String debitcard_withdraw_count_12;// 近12月取现笔数 10
	private String debitcard_withdraw_count_6_avg;// 近6月月均取现笔数 0.67
	private String debitcard_withdraw_count_12_avg;// 近12月月均取现笔数 0.83
	private String other_fee_3;// 近3月其他费用金额（元） 2.00
	private String other_fee_6;// 近6月其他费用金额（元） 3.00
	private String other_fee_12;// 近12月其他费用金额（元） 20.00
	private String other_fee_avg_6;// 近6月月均其他费用金额（元） 0.33
	private String other_fee_avg_12;// 近12月月均其他费用金额（元） 1.67
	private String regular_savings_recent_3;// 近3月定期最近一次金额（元） 4314.00
	private String regular_savings_recent_6;// 近6月定期最近一次金额（元） 3412.45
	private String regular_savings_recent_12;// 近12月定期最近一次金额（元） 5325.94
	private String regular_savings_max_3;// 近3月定期最大金额（元） 4235.49
	private String regular_savings_max_6;// 近6月定期最大金额（元） 6173.35
	private String regular_savings_max_12;// 近12月定期最大金额（元） 8293.67
	private String undue_fixed_deposit_amount_3;// 近3月未到期定期存款金额 3428.98
	private String undue_fixed_deposit_amount_6;// 近6月未到期定期存款金额 3428.90
	private String undue_fixed_deposit_amount_12;// 近12月未到期定期存款金额 3521.89
	private String undue_fixed_deposit_amount_avg_6;// 近6月月均未到期定期存款金额 8762.89
	private String undue_fixed_deposit_amount_avg_12;// 近12月月均未到期定期存款金额 9748.54
	private String fixed_deposit_amount_3;// 近3月定期存款金额 6473.67
	private String fixed_deposit_amount_6;// 近6月定期存款金额 7289.89
	private String fixed_deposit_amount_12;// 近12月定期存款金额 9289.89
	private String fixed_deposit_amount_avg_6;// 近6月月均定期存款金额 7189.89
	private String fixed_deposit_amount_avg_12;// 近12月月均定期存款金额 8289.89

	public String getDebitcard_income_3() {
		return debitcard_income_3;
	}

	public void setDebitcard_income_3(String debitcard_income_3) {
		this.debitcard_income_3 = debitcard_income_3;
	}

	public String getDebitCard_Income_6() {
		return debitCard_Income_6;
	}

	public void setDebitCard_Income_6(String debitCard_Income_6) {
		this.debitCard_Income_6 = debitCard_Income_6;
	}

	public String getDebitCard_Income_12() {
		return debitCard_Income_12;
	}

	public void setDebitCard_Income_12(String debitCard_Income_12) {
		this.debitCard_Income_12 = debitCard_Income_12;
	}

	public String getDebitcard_income_6_avg() {
		return debitcard_income_6_avg;
	}

	public void setDebitcard_income_6_avg(String debitcard_income_6_avg) {
		this.debitcard_income_6_avg = debitcard_income_6_avg;
	}

	public String getDebitcard_income_12_avg() {
		return debitcard_income_12_avg;
	}

	public void setDebitcard_income_12_avg(String debitcard_income_12_avg) {
		this.debitcard_income_12_avg = debitcard_income_12_avg;
	}

	public String getSalary_income_amount_3m() {
		return salary_income_amount_3m;
	}

	public void setSalary_income_amount_3m(String salary_income_amount_3m) {
		this.salary_income_amount_3m = salary_income_amount_3m;
	}

	public String getSalary_income_amount_6m() {
		return salary_income_amount_6m;
	}

	public void setSalary_income_amount_6m(String salary_income_amount_6m) {
		this.salary_income_amount_6m = salary_income_amount_6m;
	}

	public String getSalary_income_amount_12m() {
		return salary_income_amount_12m;
	}

	public void setSalary_income_amount_12m(String salary_income_amount_12m) {
		this.salary_income_amount_12m = salary_income_amount_12m;
	}

	public String getSalary_income_amount_average_6m() {
		return salary_income_amount_average_6m;
	}

	public void setSalary_income_amount_average_6m(String salary_income_amount_average_6m) {
		this.salary_income_amount_average_6m = salary_income_amount_average_6m;
	}

	public String getSalary_income_amount_average_12m() {
		return salary_income_amount_average_12m;
	}

	public void setSalary_income_amount_average_12m(String salary_income_amount_average_12m) {
		this.salary_income_amount_average_12m = salary_income_amount_average_12m;
	}

	public String getSalary_income_month_num_3() {
		return salary_income_month_num_3;
	}

	public void setSalary_income_month_num_3(String salary_income_month_num_3) {
		this.salary_income_month_num_3 = salary_income_month_num_3;
	}

	public String getSalary_income_month_num_6() {
		return salary_income_month_num_6;
	}

	public void setSalary_income_month_num_6(String salary_income_month_num_6) {
		this.salary_income_month_num_6 = salary_income_month_num_6;
	}

	public String getSalary_income_month_num_12() {
		return salary_income_month_num_12;
	}

	public void setSalary_income_month_num_12(String salary_income_month_num_12) {
		this.salary_income_month_num_12 = salary_income_month_num_12;
	}

	public String getLoan_in_amount_3() {
		return loan_in_amount_3;
	}

	public void setLoan_in_amount_3(String loan_in_amount_3) {
		this.loan_in_amount_3 = loan_in_amount_3;
	}

	public String getLoan_in_amount_6() {
		return loan_in_amount_6;
	}

	public void setLoan_in_amount_6(String loan_in_amount_6) {
		this.loan_in_amount_6 = loan_in_amount_6;
	}

	public String getLoan_in_amount_12() {
		return loan_in_amount_12;
	}

	public void setLoan_in_amount_12(String loan_in_amount_12) {
		this.loan_in_amount_12 = loan_in_amount_12;
	}

	public String getLoan_in_amount_avg_6() {
		return loan_in_amount_avg_6;
	}

	public void setLoan_in_amount_avg_6(String loan_in_amount_avg_6) {
		this.loan_in_amount_avg_6 = loan_in_amount_avg_6;
	}

	public String getLoan_in_amount_avg_12() {
		return loan_in_amount_avg_12;
	}

	public void setLoan_in_amount_avg_12(String loan_in_amount_avg_12) {
		this.loan_in_amount_avg_12 = loan_in_amount_avg_12;
	}

	public String getLoan_in_month_num_3() {
		return loan_in_month_num_3;
	}

	public void setLoan_in_month_num_3(String loan_in_month_num_3) {
		this.loan_in_month_num_3 = loan_in_month_num_3;
	}

	public String getLoan_in_month_num_6() {
		return loan_in_month_num_6;
	}

	public void setLoan_in_month_num_6(String loan_in_month_num_6) {
		this.loan_in_month_num_6 = loan_in_month_num_6;
	}

	public String getLoan_in_month_num_12() {
		return loan_in_month_num_12;
	}

	public void setLoan_in_month_num_12(String loan_in_month_num_12) {
		this.loan_in_month_num_12 = loan_in_month_num_12;
	}

	public String getDebitcard_outcome_3() {
		return debitcard_outcome_3;
	}

	public void setDebitcard_outcome_3(String debitcard_outcome_3) {
		this.debitcard_outcome_3 = debitcard_outcome_3;
	}

	public String getDebitcard_outcome_6() {
		return debitcard_outcome_6;
	}

	public void setDebitcard_outcome_6(String debitcard_outcome_6) {
		this.debitcard_outcome_6 = debitcard_outcome_6;
	}

	public String getDebitcard_outcome_12() {
		return debitcard_outcome_12;
	}

	public void setDebitcard_outcome_12(String debitcard_outcome_12) {
		this.debitcard_outcome_12 = debitcard_outcome_12;
	}

	public String getDebitcard_outcome_6_avg() {
		return debitcard_outcome_6_avg;
	}

	public void setDebitcard_outcome_6_avg(String debitcard_outcome_6_avg) {
		this.debitcard_outcome_6_avg = debitcard_outcome_6_avg;
	}

	public String getDebitcard_outcome_12_avg() {
		return debitcard_outcome_12_avg;
	}

	public void setDebitcard_outcome_12_avg(String debitcard_outcome_12_avg) {
		this.debitcard_outcome_12_avg = debitcard_outcome_12_avg;
	}

	public String getRepany_loan_amount_3() {
		return repany_loan_amount_3;
	}

	public void setRepany_loan_amount_3(String repany_loan_amount_3) {
		this.repany_loan_amount_3 = repany_loan_amount_3;
	}

	public String getRepany_loan_amount_6() {
		return repany_loan_amount_6;
	}

	public void setRepany_loan_amount_6(String repany_loan_amount_6) {
		this.repany_loan_amount_6 = repany_loan_amount_6;
	}

	public String getRepany_loan_amount_12() {
		return repany_loan_amount_12;
	}

	public void setRepany_loan_amount_12(String repany_loan_amount_12) {
		this.repany_loan_amount_12 = repany_loan_amount_12;
	}

	public String getRepany_loan_amount_avg_6() {
		return repany_loan_amount_avg_6;
	}

	public void setRepany_loan_amount_avg_6(String repany_loan_amount_avg_6) {
		this.repany_loan_amount_avg_6 = repany_loan_amount_avg_6;
	}

	public String getRepany_loan_amount_avg_12() {
		return repany_loan_amount_avg_12;
	}

	public void setRepany_loan_amount_avg_12(String repany_loan_amount_avg_12) {
		this.repany_loan_amount_avg_12 = repany_loan_amount_avg_12;
	}

	public String getRepany_loan_mon_num_3() {
		return repany_loan_mon_num_3;
	}

	public void setRepany_loan_mon_num_3(String repany_loan_mon_num_3) {
		this.repany_loan_mon_num_3 = repany_loan_mon_num_3;
	}

	public String getRepany_loan_mon_num_6() {
		return repany_loan_mon_num_6;
	}

	public void setRepany_loan_mon_num_6(String repany_loan_mon_num_6) {
		this.repany_loan_mon_num_6 = repany_loan_mon_num_6;
	}

	public String getRepany_loan_mon_num_12() {
		return repany_loan_mon_num_12;
	}

	public void setRepany_loan_mon_num_12(String repany_loan_mon_num_12) {
		this.repany_loan_mon_num_12 = repany_loan_mon_num_12;
	}

	public String getDebitcard_in_num_trans_num_ratio_3() {
		return debitcard_in_num_trans_num_ratio_3;
	}

	public void setDebitcard_in_num_trans_num_ratio_3(String debitcard_in_num_trans_num_ratio_3) {
		this.debitcard_in_num_trans_num_ratio_3 = debitcard_in_num_trans_num_ratio_3;
	}

	public String getDebitcard_in_num_trans_num_ratio_6() {
		return debitcard_in_num_trans_num_ratio_6;
	}

	public void setDebitcard_in_num_trans_num_ratio_6(String debitcard_in_num_trans_num_ratio_6) {
		this.debitcard_in_num_trans_num_ratio_6 = debitcard_in_num_trans_num_ratio_6;
	}

	public String getDebitcard_in_num_trans_num_ratio_12() {
		return debitcard_in_num_trans_num_ratio_12;
	}

	public void setDebitcard_in_num_trans_num_ratio_12(String debitcard_in_num_trans_num_ratio_12) {
		this.debitcard_in_num_trans_num_ratio_12 = debitcard_in_num_trans_num_ratio_12;
	}

	public String getDebitcard_max_balance_3() {
		return debitcard_max_balance_3;
	}

	public void setDebitcard_max_balance_3(String debitcard_max_balance_3) {
		this.debitcard_max_balance_3 = debitcard_max_balance_3;
	}

	public String getDebitcard_max_balance_6() {
		return debitcard_max_balance_6;
	}

	public void setDebitcard_max_balance_6(String debitcard_max_balance_6) {
		this.debitcard_max_balance_6 = debitcard_max_balance_6;
	}

	public String getDebitcard_max_balance_12() {
		return debitcard_max_balance_12;
	}

	public void setDebitcard_max_balance_12(String debitcard_max_balance_12) {
		this.debitcard_max_balance_12 = debitcard_max_balance_12;
	}

	public String getDebitcard_recently_balance_3() {
		return debitcard_recently_balance_3;
	}

	public void setDebitcard_recently_balance_3(String debitcard_recently_balance_3) {
		this.debitcard_recently_balance_3 = debitcard_recently_balance_3;
	}

	public String getDebitcard_recently_balance_6() {
		return debitcard_recently_balance_6;
	}

	public void setDebitcard_recently_balance_6(String debitcard_recently_balance_6) {
		this.debitcard_recently_balance_6 = debitcard_recently_balance_6;
	}

	public String getDebitcard_recently_balance_12() {
		return debitcard_recently_balance_12;
	}

	public void setDebitcard_recently_balance_12(String debitcard_recently_balance_12) {
		this.debitcard_recently_balance_12 = debitcard_recently_balance_12;
	}

	public String getDebitcard_consume_amount_3() {
		return debitcard_consume_amount_3;
	}

	public void setDebitcard_consume_amount_3(String debitcard_consume_amount_3) {
		this.debitcard_consume_amount_3 = debitcard_consume_amount_3;
	}

	public String getDebitcard_consume_amount_6() {
		return debitcard_consume_amount_6;
	}

	public void setDebitcard_consume_amount_6(String debitcard_consume_amount_6) {
		this.debitcard_consume_amount_6 = debitcard_consume_amount_6;
	}

	public String getDebitcard_consume_amount_12() {
		return debitcard_consume_amount_12;
	}

	public void setDebitcard_consume_amount_12(String debitcard_consume_amount_12) {
		this.debitcard_consume_amount_12 = debitcard_consume_amount_12;
	}

	public String getDebitcard_consume_amount_6_avg() {
		return debitcard_consume_amount_6_avg;
	}

	public void setDebitcard_consume_amount_6_avg(String debitcard_consume_amount_6_avg) {
		this.debitcard_consume_amount_6_avg = debitcard_consume_amount_6_avg;
	}

	public String getDebitcard_consume_amount_12_avg() {
		return debitcard_consume_amount_12_avg;
	}

	public void setDebitcard_consume_amount_12_avg(String debitcard_consume_amount_12_avg) {
		this.debitcard_consume_amount_12_avg = debitcard_consume_amount_12_avg;
	}

	public String getDebitcard_consume_count_3() {
		return debitcard_consume_count_3;
	}

	public void setDebitcard_consume_count_3(String debitcard_consume_count_3) {
		this.debitcard_consume_count_3 = debitcard_consume_count_3;
	}

	public String getDebitcard_consume_count_6() {
		return debitcard_consume_count_6;
	}

	public void setDebitcard_consume_count_6(String debitcard_consume_count_6) {
		this.debitcard_consume_count_6 = debitcard_consume_count_6;
	}

	public String getDebitcard_consume_count_12() {
		return debitcard_consume_count_12;
	}

	public void setDebitcard_consume_count_12(String debitcard_consume_count_12) {
		this.debitcard_consume_count_12 = debitcard_consume_count_12;
	}

	public String getDebitcard_consume_count_6_avg() {
		return debitcard_consume_count_6_avg;
	}

	public void setDebitcard_consume_count_6_avg(String debitcard_consume_count_6_avg) {
		this.debitcard_consume_count_6_avg = debitcard_consume_count_6_avg;
	}

	public String getDebitcard_consume_count_12_avg() {
		return debitcard_consume_count_12_avg;
	}

	public void setDebitcard_consume_count_12_avg(String debitcard_consume_count_12_avg) {
		this.debitcard_consume_count_12_avg = debitcard_consume_count_12_avg;
	}

	public String getDebitcard_max_continue_consume_month_3() {
		return debitcard_max_continue_consume_month_3;
	}

	public void setDebitcard_max_continue_consume_month_3(String debitcard_max_continue_consume_month_3) {
		this.debitcard_max_continue_consume_month_3 = debitcard_max_continue_consume_month_3;
	}

	public String getDebitcard_max_continue_consume_month_6() {
		return debitcard_max_continue_consume_month_6;
	}

	public void setDebitcard_max_continue_consume_month_6(String debitcard_max_continue_consume_month_6) {
		this.debitcard_max_continue_consume_month_6 = debitcard_max_continue_consume_month_6;
	}

	public String getDebitcard_max_continue_consume_month_12() {
		return debitcard_max_continue_consume_month_12;
	}

	public void setDebitcard_max_continue_consume_month_12(String debitcard_max_continue_consume_month_12) {
		this.debitcard_max_continue_consume_month_12 = debitcard_max_continue_consume_month_12;
	}

	public String getDebitcard_withdraw_amount_3() {
		return debitcard_withdraw_amount_3;
	}

	public void setDebitcard_withdraw_amount_3(String debitcard_withdraw_amount_3) {
		this.debitcard_withdraw_amount_3 = debitcard_withdraw_amount_3;
	}

	public String getDebitcard_withdraw_amount_6() {
		return debitcard_withdraw_amount_6;
	}

	public void setDebitcard_withdraw_amount_6(String debitcard_withdraw_amount_6) {
		this.debitcard_withdraw_amount_6 = debitcard_withdraw_amount_6;
	}

	public String getDebitcard_withdraw_amount_12() {
		return debitcard_withdraw_amount_12;
	}

	public void setDebitcard_withdraw_amount_12(String debitcard_withdraw_amount_12) {
		this.debitcard_withdraw_amount_12 = debitcard_withdraw_amount_12;
	}

	public String getDebitcard_withdraw_amount_6_avg() {
		return debitcard_withdraw_amount_6_avg;
	}

	public void setDebitcard_withdraw_amount_6_avg(String debitcard_withdraw_amount_6_avg) {
		this.debitcard_withdraw_amount_6_avg = debitcard_withdraw_amount_6_avg;
	}

	public String getDebitcard_withdraw_amount_12_avg() {
		return debitcard_withdraw_amount_12_avg;
	}

	public void setDebitcard_withdraw_amount_12_avg(String debitcard_withdraw_amount_12_avg) {
		this.debitcard_withdraw_amount_12_avg = debitcard_withdraw_amount_12_avg;
	}

	public String getDebitcard_withdraw_count_3() {
		return debitcard_withdraw_count_3;
	}

	public void setDebitcard_withdraw_count_3(String debitcard_withdraw_count_3) {
		this.debitcard_withdraw_count_3 = debitcard_withdraw_count_3;
	}

	public String getDebitcard_withdraw_count_6() {
		return debitcard_withdraw_count_6;
	}

	public void setDebitcard_withdraw_count_6(String debitcard_withdraw_count_6) {
		this.debitcard_withdraw_count_6 = debitcard_withdraw_count_6;
	}

	public String getDebitcard_withdraw_count_12() {
		return debitcard_withdraw_count_12;
	}

	public void setDebitcard_withdraw_count_12(String debitcard_withdraw_count_12) {
		this.debitcard_withdraw_count_12 = debitcard_withdraw_count_12;
	}

	public String getDebitcard_withdraw_count_6_avg() {
		return debitcard_withdraw_count_6_avg;
	}

	public void setDebitcard_withdraw_count_6_avg(String debitcard_withdraw_count_6_avg) {
		this.debitcard_withdraw_count_6_avg = debitcard_withdraw_count_6_avg;
	}

	public String getDebitcard_withdraw_count_12_avg() {
		return debitcard_withdraw_count_12_avg;
	}

	public void setDebitcard_withdraw_count_12_avg(String debitcard_withdraw_count_12_avg) {
		this.debitcard_withdraw_count_12_avg = debitcard_withdraw_count_12_avg;
	}

	public String getOther_fee_3() {
		return other_fee_3;
	}

	public void setOther_fee_3(String other_fee_3) {
		this.other_fee_3 = other_fee_3;
	}

	public String getOther_fee_6() {
		return other_fee_6;
	}

	public void setOther_fee_6(String other_fee_6) {
		this.other_fee_6 = other_fee_6;
	}

	public String getOther_fee_12() {
		return other_fee_12;
	}

	public void setOther_fee_12(String other_fee_12) {
		this.other_fee_12 = other_fee_12;
	}

	public String getOther_fee_avg_6() {
		return other_fee_avg_6;
	}

	public void setOther_fee_avg_6(String other_fee_avg_6) {
		this.other_fee_avg_6 = other_fee_avg_6;
	}

	public String getOther_fee_avg_12() {
		return other_fee_avg_12;
	}

	public void setOther_fee_avg_12(String other_fee_avg_12) {
		this.other_fee_avg_12 = other_fee_avg_12;
	}

	public String getRegular_savings_recent_3() {
		return regular_savings_recent_3;
	}

	public void setRegular_savings_recent_3(String regular_savings_recent_3) {
		this.regular_savings_recent_3 = regular_savings_recent_3;
	}

	public String getRegular_savings_recent_6() {
		return regular_savings_recent_6;
	}

	public void setRegular_savings_recent_6(String regular_savings_recent_6) {
		this.regular_savings_recent_6 = regular_savings_recent_6;
	}

	public String getRegular_savings_recent_12() {
		return regular_savings_recent_12;
	}

	public void setRegular_savings_recent_12(String regular_savings_recent_12) {
		this.regular_savings_recent_12 = regular_savings_recent_12;
	}

	public String getRegular_savings_max_3() {
		return regular_savings_max_3;
	}

	public void setRegular_savings_max_3(String regular_savings_max_3) {
		this.regular_savings_max_3 = regular_savings_max_3;
	}

	public String getRegular_savings_max_6() {
		return regular_savings_max_6;
	}

	public void setRegular_savings_max_6(String regular_savings_max_6) {
		this.regular_savings_max_6 = regular_savings_max_6;
	}

	public String getRegular_savings_max_12() {
		return regular_savings_max_12;
	}

	public void setRegular_savings_max_12(String regular_savings_max_12) {
		this.regular_savings_max_12 = regular_savings_max_12;
	}

	public String getUndue_fixed_deposit_amount_3() {
		return undue_fixed_deposit_amount_3;
	}

	public void setUndue_fixed_deposit_amount_3(String undue_fixed_deposit_amount_3) {
		this.undue_fixed_deposit_amount_3 = undue_fixed_deposit_amount_3;
	}

	public String getUndue_fixed_deposit_amount_6() {
		return undue_fixed_deposit_amount_6;
	}

	public void setUndue_fixed_deposit_amount_6(String undue_fixed_deposit_amount_6) {
		this.undue_fixed_deposit_amount_6 = undue_fixed_deposit_amount_6;
	}

	public String getUndue_fixed_deposit_amount_12() {
		return undue_fixed_deposit_amount_12;
	}

	public void setUndue_fixed_deposit_amount_12(String undue_fixed_deposit_amount_12) {
		this.undue_fixed_deposit_amount_12 = undue_fixed_deposit_amount_12;
	}

	public String getUndue_fixed_deposit_amount_avg_6() {
		return undue_fixed_deposit_amount_avg_6;
	}

	public void setUndue_fixed_deposit_amount_avg_6(String undue_fixed_deposit_amount_avg_6) {
		this.undue_fixed_deposit_amount_avg_6 = undue_fixed_deposit_amount_avg_6;
	}

	public String getUndue_fixed_deposit_amount_avg_12() {
		return undue_fixed_deposit_amount_avg_12;
	}

	public void setUndue_fixed_deposit_amount_avg_12(String undue_fixed_deposit_amount_avg_12) {
		this.undue_fixed_deposit_amount_avg_12 = undue_fixed_deposit_amount_avg_12;
	}

	public String getFixed_deposit_amount_3() {
		return fixed_deposit_amount_3;
	}

	public void setFixed_deposit_amount_3(String fixed_deposit_amount_3) {
		this.fixed_deposit_amount_3 = fixed_deposit_amount_3;
	}

	public String getFixed_deposit_amount_6() {
		return fixed_deposit_amount_6;
	}

	public void setFixed_deposit_amount_6(String fixed_deposit_amount_6) {
		this.fixed_deposit_amount_6 = fixed_deposit_amount_6;
	}

	public String getFixed_deposit_amount_12() {
		return fixed_deposit_amount_12;
	}

	public void setFixed_deposit_amount_12(String fixed_deposit_amount_12) {
		this.fixed_deposit_amount_12 = fixed_deposit_amount_12;
	}

	public String getFixed_deposit_amount_avg_6() {
		return fixed_deposit_amount_avg_6;
	}

	public void setFixed_deposit_amount_avg_6(String fixed_deposit_amount_avg_6) {
		this.fixed_deposit_amount_avg_6 = fixed_deposit_amount_avg_6;
	}

	public String getFixed_deposit_amount_avg_12() {
		return fixed_deposit_amount_avg_12;
	}

	public void setFixed_deposit_amount_avg_12(String fixed_deposit_amount_avg_12) {
		this.fixed_deposit_amount_avg_12 = fixed_deposit_amount_avg_12;
	}

}

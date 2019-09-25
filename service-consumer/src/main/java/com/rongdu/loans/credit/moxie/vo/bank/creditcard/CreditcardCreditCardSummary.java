package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 1.3 交易行为 (credit_card_summary)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardCreditCardSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String total_new_balance_1;// 当期总透支余额（元） 962.91
	private String total_last_balance_1;// 上期应还总额（元） 759.38
	private String total_min_payment_1;// 当期最低还款总额（元） 290.66
	private String total_consume_amount_1;// 当期新发生消费总金额（元 123.78
	private String total_consume_num_1;// 当期新发生消费总笔数 5
	private String last_unrepay_amount;// 上期未还总额（元） 782.66
	private String cur_consume_avg_amount;// 当期新发生消费平均金额（元） 213.56
	private String cur_cash_amount;// 当期新发生提现总金额（元） 452.87
	private String cur_cash_num;// 当期新发生提现总笔数 4
	private String cur_cash_avg_amount;// 当期新发生提现平均金额（元） 647.89

	public String getTotal_new_balance_1() {
		return total_new_balance_1;
	}

	public void setTotal_new_balance_1(String total_new_balance_1) {
		this.total_new_balance_1 = total_new_balance_1;
	}

	public String getTotal_last_balance_1() {
		return total_last_balance_1;
	}

	public void setTotal_last_balance_1(String total_last_balance_1) {
		this.total_last_balance_1 = total_last_balance_1;
	}

	public String getTotal_min_payment_1() {
		return total_min_payment_1;
	}

	public void setTotal_min_payment_1(String total_min_payment_1) {
		this.total_min_payment_1 = total_min_payment_1;
	}

	public String getTotal_consume_amount_1() {
		return total_consume_amount_1;
	}

	public void setTotal_consume_amount_1(String total_consume_amount_1) {
		this.total_consume_amount_1 = total_consume_amount_1;
	}

	public String getTotal_consume_num_1() {
		return total_consume_num_1;
	}

	public void setTotal_consume_num_1(String total_consume_num_1) {
		this.total_consume_num_1 = total_consume_num_1;
	}

	public String getLast_unrepay_amount() {
		return last_unrepay_amount;
	}

	public void setLast_unrepay_amount(String last_unrepay_amount) {
		this.last_unrepay_amount = last_unrepay_amount;
	}

	public String getCur_consume_avg_amount() {
		return cur_consume_avg_amount;
	}

	public void setCur_consume_avg_amount(String cur_consume_avg_amount) {
		this.cur_consume_avg_amount = cur_consume_avg_amount;
	}

	public String getCur_cash_amount() {
		return cur_cash_amount;
	}

	public void setCur_cash_amount(String cur_cash_amount) {
		this.cur_cash_amount = cur_cash_amount;
	}

	public String getCur_cash_num() {
		return cur_cash_num;
	}

	public void setCur_cash_num(String cur_cash_num) {
		this.cur_cash_num = cur_cash_num;
	}

	public String getCur_cash_avg_amount() {
		return cur_cash_avg_amount;
	}

	public void setCur_cash_avg_amount(String cur_cash_avg_amount) {
		this.cur_cash_avg_amount = cur_cash_avg_amount;
	}

}

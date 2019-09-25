package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 交易行为
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportTransactionInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String total_current_consume; // 当期新发生消费总金额
	private String total_current_new_balance;// 当期总透支余额
	private String total_last_new_balance; // 上期应还总额
	private String current_total_min_payment; // 当期最低还款总额
	private String last_no_payment_total; // 上期未还总额
	private String current_total_consume_count; // 当期新发生消费总笔数
	private String current_consume_average_amount; // 当期新发生消费平均金额
	private String current_withdraw_amount; // 当期新发生提现总金额
	private String current_withdraw_count; // 当期新发生提现总笔数
	private String current_withdraw_average_amount; // 当期新发生提现平均金额

	public String getTotal_current_consume() {
		return total_current_consume;
	}

	public void setTotal_current_consume(String total_current_consume) {
		this.total_current_consume = total_current_consume;
	}

	public String getTotal_current_new_balance() {
		return total_current_new_balance;
	}

	public void setTotal_current_new_balance(String total_current_new_balance) {
		this.total_current_new_balance = total_current_new_balance;
	}

	public String getTotal_last_new_balance() {
		return total_last_new_balance;
	}

	public void setTotal_last_new_balance(String total_last_new_balance) {
		this.total_last_new_balance = total_last_new_balance;
	}

	public String getCurrent_total_min_payment() {
		return current_total_min_payment;
	}

	public void setCurrent_total_min_payment(String current_total_min_payment) {
		this.current_total_min_payment = current_total_min_payment;
	}

	public String getLast_no_payment_total() {
		return last_no_payment_total;
	}

	public void setLast_no_payment_total(String last_no_payment_total) {
		this.last_no_payment_total = last_no_payment_total;
	}

	public String getCurrent_total_consume_count() {
		return current_total_consume_count;
	}

	public void setCurrent_total_consume_count(String current_total_consume_count) {
		this.current_total_consume_count = current_total_consume_count;
	}

	public String getCurrent_consume_average_amount() {
		return current_consume_average_amount;
	}

	public void setCurrent_consume_average_amount(String current_consume_average_amount) {
		this.current_consume_average_amount = current_consume_average_amount;
	}

	public String getCurrent_withdraw_amount() {
		return current_withdraw_amount;
	}

	public void setCurrent_withdraw_amount(String current_withdraw_amount) {
		this.current_withdraw_amount = current_withdraw_amount;
	}

	public String getCurrent_withdraw_count() {
		return current_withdraw_count;
	}

	public void setCurrent_withdraw_count(String current_withdraw_count) {
		this.current_withdraw_count = current_withdraw_count;
	}

	public String getCurrent_withdraw_average_amount() {
		return current_withdraw_average_amount;
	}

	public void setCurrent_withdraw_average_amount(String current_withdraw_average_amount) {
		this.current_withdraw_average_amount = current_withdraw_average_amount;
	}

}

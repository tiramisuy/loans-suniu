package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 1.2 授信情况 account_summary
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardAccountSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String creditcard_limit;// 总信用额（元） 16000.00
	private String total_can_use_consume_limit_1;// 总可用信用额（元） 15037
	private String creditcard_balance;// 可用消费信用额（元） 13834
	private String creditcard_cash_limit;// 提现信用额（元） 12871.25
	private String creditcard_cash_balance;// 可用提现信用额（元） 13834.16
	private String consume_credit_limit;// 消费信用额（元） 16000.00
	private String single_bank_max_limit;// 单一银行最高授信额度（元） 16000.00
	private String single_bank_min_limit;// 单一银行最低授信额度（元） 16000.00

	public String getCreditcard_limit() {
		return creditcard_limit;
	}

	public void setCreditcard_limit(String creditcard_limit) {
		this.creditcard_limit = creditcard_limit;
	}

	public String getTotal_can_use_consume_limit_1() {
		return total_can_use_consume_limit_1;
	}

	public void setTotal_can_use_consume_limit_1(String total_can_use_consume_limit_1) {
		this.total_can_use_consume_limit_1 = total_can_use_consume_limit_1;
	}

	public String getCreditcard_balance() {
		return creditcard_balance;
	}

	public void setCreditcard_balance(String creditcard_balance) {
		this.creditcard_balance = creditcard_balance;
	}

	public String getCreditcard_cash_limit() {
		return creditcard_cash_limit;
	}

	public void setCreditcard_cash_limit(String creditcard_cash_limit) {
		this.creditcard_cash_limit = creditcard_cash_limit;
	}

	public String getCreditcard_cash_balance() {
		return creditcard_cash_balance;
	}

	public void setCreditcard_cash_balance(String creditcard_cash_balance) {
		this.creditcard_cash_balance = creditcard_cash_balance;
	}

	public String getConsume_credit_limit() {
		return consume_credit_limit;
	}

	public void setConsume_credit_limit(String consume_credit_limit) {
		this.consume_credit_limit = consume_credit_limit;
	}

	public String getSingle_bank_max_limit() {
		return single_bank_max_limit;
	}

	public void setSingle_bank_max_limit(String single_bank_max_limit) {
		this.single_bank_max_limit = single_bank_max_limit;
	}

	public String getSingle_bank_min_limit() {
		return single_bank_min_limit;
	}

	public void setSingle_bank_min_limit(String single_bank_min_limit) {
		this.single_bank_min_limit = single_bank_min_limit;
	}

}

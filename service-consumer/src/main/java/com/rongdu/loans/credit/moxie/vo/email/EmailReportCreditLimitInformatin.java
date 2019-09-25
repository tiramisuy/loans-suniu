package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 授信情况
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportCreditLimitInformatin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String total_credit_limit;// 总信用额
	private String total_consume_credit_limit;// 消费信用额
	private String total_cash_limit;// 提现信用额
	private String aviable_cash_limit;	// 可用提现信用额
	private String aviable_consume_credit_limit;// 可用消费信用额
	private String total_aviable_credit_limit;// 总可用信用额
	private String max_total_credit_limit;	// 单一银行最高授信额度
	private String min_total_credit_limit;// 单一银行最低授信额度

	public String getTotal_credit_limit() {
		return total_credit_limit;
	}

	public void setTotal_credit_limit(String total_credit_limit) {
		this.total_credit_limit = total_credit_limit;
	}

	public String getTotal_consume_credit_limit() {
		return total_consume_credit_limit;
	}

	public void setTotal_consume_credit_limit(String total_consume_credit_limit) {
		this.total_consume_credit_limit = total_consume_credit_limit;
	}

	public String getTotal_cash_limit() {
		return total_cash_limit;
	}

	public void setTotal_cash_limit(String total_cash_limit) {
		this.total_cash_limit = total_cash_limit;
	}

	public String getAviable_cash_limit() {
		return aviable_cash_limit;
	}

	public void setAviable_cash_limit(String aviable_cash_limit) {
		this.aviable_cash_limit = aviable_cash_limit;
	}

	public String getAviable_consume_credit_limit() {
		return aviable_consume_credit_limit;
	}

	public void setAviable_consume_credit_limit(String aviable_consume_credit_limit) {
		this.aviable_consume_credit_limit = aviable_consume_credit_limit;
	}

	public String getTotal_aviable_credit_limit() {
		return total_aviable_credit_limit;
	}

	public void setTotal_aviable_credit_limit(String total_aviable_credit_limit) {
		this.total_aviable_credit_limit = total_aviable_credit_limit;
	}

	public String getMax_total_credit_limit() {
		return max_total_credit_limit;
	}

	public void setMax_total_credit_limit(String max_total_credit_limit) {
		this.max_total_credit_limit = max_total_credit_limit;
	}

	public String getMin_total_credit_limit() {
		return min_total_credit_limit;
	}

	public void setMin_total_credit_limit(String min_total_credit_limit) {
		this.min_total_credit_limit = min_total_credit_limit;
	}

}

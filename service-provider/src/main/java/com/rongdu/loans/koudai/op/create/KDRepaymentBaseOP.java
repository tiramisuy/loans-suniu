package com.rongdu.loans.koudai.op.create;

import java.io.Serializable;

public class KDRepaymentBaseOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer repayment_type;//还款方式，0:等本等息,1:等额本息,2:一次性还款

	private Integer repayment_amount;//总还款金额,单位：分
	private Integer repayment_time;//最迟还款日期时间戳
	private Integer period;//总还款期数
	private Integer repayment_principal;//还款本金,单位：分
	private Integer repayment_interest;//还款利息,单位：分
	
	public Integer getRepayment_type() {
		return repayment_type;
	}
	public void setRepayment_type(Integer repayment_type) {
		this.repayment_type = repayment_type;
	}
	public Integer getRepayment_amount() {
		return repayment_amount;
	}
	public void setRepayment_amount(Integer repayment_amount) {
		this.repayment_amount = repayment_amount;
	}
	public Integer getRepayment_time() {
		return repayment_time;
	}
	public void setRepayment_time(Integer repayment_time) {
		this.repayment_time = repayment_time;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getRepayment_principal() {
		return repayment_principal;
	}
	public void setRepayment_principal(Integer repayment_principal) {
		this.repayment_principal = repayment_principal;
	}
	public Integer getRepayment_interest() {
		return repayment_interest;
	}
	public void setRepayment_interest(Integer repayment_interest) {
		this.repayment_interest = repayment_interest;
	}	
	
}

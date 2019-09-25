package com.rongdu.loans.koudai.op.create;

import java.io.Serializable;

public class KDPeriodBaseOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer period;//第几期
	private Integer plan_repayment_money;//预期还款金额,单位：分
	private Integer plan_repayment_time;//预期还款时间戳
	private Integer plan_repayment_principal;//预期还款本金,单位：分
	private Integer plan_repayment_interest;//预期还款利息,单位：分
	private Float apr;//年利率，百分之几
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getPlan_repayment_money() {
		return plan_repayment_money;
	}
	public void setPlan_repayment_money(Integer plan_repayment_money) {
		this.plan_repayment_money = plan_repayment_money;
	}
	public Integer getPlan_repayment_time() {
		return plan_repayment_time;
	}
	public void setPlan_repayment_time(Integer plan_repayment_time) {
		this.plan_repayment_time = plan_repayment_time;
	}
	public Integer getPlan_repayment_principal() {
		return plan_repayment_principal;
	}
	public void setPlan_repayment_principal(Integer plan_repayment_principal) {
		this.plan_repayment_principal = plan_repayment_principal;
	}
	public Integer getPlan_repayment_interest() {
		return plan_repayment_interest;
	}
	public void setPlan_repayment_interest(Integer plan_repayment_interest) {
		this.plan_repayment_interest = plan_repayment_interest;
	}
	public Float getApr() {
		return apr;
	}
	public void setApr(Float apr) {
		this.apr = apr;
	}
	
	
	
}

package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
* @Description:  还款计划详细信息	
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public class KDPeriodBaseOP  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 第几期
	 */
	private Integer period;
	/**
	 * 预期还款金额
	 */
	private Integer planRepaymentMoney;
	/**
	 * 预期还款时间戮
	 */
	private Integer planRepaymentTime;
	/**
	 * 预期还款本金
	 */
	private Integer planRepaymentPrincipal;
	/**
	 * 预期还款利息
	 */
	private Integer planRepaymentInterest;
	/**
	 * 年利率
	 */
	private Float apr;
	
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getPlanRepaymentMoney() {
		return planRepaymentMoney;
	}
	public void setPlanRepaymentMoney(Integer planRepaymentMoney) {
		this.planRepaymentMoney = planRepaymentMoney;
	}
	public Integer getPlanRepaymentTime() {
		return planRepaymentTime;
	}
	public void setPlanRepaymentTime(Integer planRepaymentTime) {
		this.planRepaymentTime = planRepaymentTime;
	}
	public Integer getPlanRepaymentPrincipal() {
		return planRepaymentPrincipal;
	}
	public void setPlanRepaymentPrincipal(Integer planRepaymentPrincipal) {
		this.planRepaymentPrincipal = planRepaymentPrincipal;
	}
	public Integer getPlanRepaymentInterest() {
		return planRepaymentInterest;
	}
	public void setPlanRepaymentInterest(Integer planRepaymentInterest) {
		this.planRepaymentInterest = planRepaymentInterest;
	}
	public Float getApr() {
		return apr;
	}
	public void setApr(Float apr) {
		this.apr = apr;
	}

	
	
	
}

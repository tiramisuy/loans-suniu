package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;

/**
 * 
* @Description:  总还款计划信息
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public class KDRepaymentBaseOP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *还款方式 
	 *0-等本等息 1-等额本息 2-一次性还款 
	 */
	private Integer repaymentType;
	/**
	 * 总还款金额，单位:分
	 */
	private Integer repaymentAmount;
	/**
	 * 最迟还款日期时间戮	
	 */
	private Integer repaymentTime;
	/**
	 * 总还款期数
	 */
	private Integer period;
	/**
	 * 	还款本金
	 */
	private Integer repaymentPrincipal;
	/**
	 * 	还款利息
	 */
	private Integer repaymentInterest;

	public Integer getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Integer getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(Integer repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public Integer getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Integer repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getRepaymentPrincipal() {
		return repaymentPrincipal;
	}

	public void setRepaymentPrincipal(Integer repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}

	public Integer getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(Integer repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}

}

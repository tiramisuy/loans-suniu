/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithholdRepayPlanQueryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 应还金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 应还本金
	 */
	private BigDecimal principal;
	/**
	 * 应还利息
	 */
	private BigDecimal interest;
	/**
	 * 逾期管理费
	 */
	private BigDecimal overdueFee;
	/**
	 * 逾期罚息
	 */
	private BigDecimal penalty;
	/**
	 * 减免
	 */
	private BigDecimal deduction;
	/**
	 * 还款明细id
	 */
	private String repayPlanItemId;
	/**
	 * 还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
	 */
	private Integer type;
	/**
	 * 逾期天数
	 */
	private int overdueDays;

	/**
	 * 当前实际已还
	 */
	private BigDecimal currActualRepayAmt;
	/**
	 * 当前应还
	 * 
	 * @return
	 */
	private BigDecimal currRepayAmt;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}

	public BigDecimal getCurrActualRepayAmt() {
		return currActualRepayAmt;
	}

	public void setCurrActualRepayAmt(BigDecimal currActualRepayAmt) {
		this.currActualRepayAmt = currActualRepayAmt;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getCurrRepayAmt() {
		return currRepayAmt;
	}

	public void setCurrRepayAmt(BigDecimal currRepayAmt) {
		this.currRepayAmt = currRepayAmt;
	}

}
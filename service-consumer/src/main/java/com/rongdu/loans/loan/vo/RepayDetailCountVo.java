package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RepayDetailCountVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3707703291166360856L;

	/**
	 * 当前期数
	 */
	private String thisTerm;
	/**
	 * 还款日期
	 */
	private String repayDate;
	/**
	 * 应还笔数
	 */
	private BigDecimal repayCount;
	/**
	 * 应还总额
	 */
	private BigDecimal repayAmount;
	/**
	 * 已还笔数
	 */
	private BigDecimal finishRepay;
	/**
	 * 未还笔数
	 */
	private BigDecimal unRepay;
	/**
	 * 已还比例
	 */
	private BigDecimal finishRepayRate;
	/**
	 * 未还比例
	 */
	private BigDecimal unRepayRate;
	/**
	 * 已还金额
	 */
	private BigDecimal finishRepayAmount;
	/**
	 * 未还金额
	 */
	private BigDecimal unRepayAmount;
	/**
	 * 已还金额比例
	 */
	private BigDecimal finishRepayAmountRate;
	/**
	 * 未还金额比例
	 */
	private BigDecimal unRepayAmountRate;
	/**
	 * 贷款次数
	 */
	private String loanCount;
	/**
	 * 渠道
	 */
	private String channel;
	
	public String getThisTerm() {
		return thisTerm;
	}
	public void setThisTerm(String thisTerm) {
		this.thisTerm = thisTerm;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public BigDecimal getRepayCount() {
		return repayCount;
	}
	public void setRepayCount(BigDecimal repayCount) {
		this.repayCount = repayCount;
	}
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
	public BigDecimal getFinishRepay() {
		return finishRepay;
	}
	public void setFinishRepay(BigDecimal finishRepay) {
		this.finishRepay = finishRepay;
	}
	public BigDecimal getUnRepay() {
		return unRepay;
	}
	public void setUnRepay(BigDecimal unRepay) {
		this.unRepay = unRepay;
	}
	public BigDecimal getFinishRepayRate() {
		return finishRepayRate;
	}
	public void setFinishRepayRate(BigDecimal finishRepayRate) {
		this.finishRepayRate = finishRepayRate;
	}
	public BigDecimal getUnRepayRate() {
		return unRepayRate;
	}
	public void setUnRepayRate(BigDecimal unRepayRate) {
		this.unRepayRate = unRepayRate;
	}
	public BigDecimal getFinishRepayAmount() {
		return finishRepayAmount;
	}
	public void setFinishRepayAmount(BigDecimal finishRepayAmount) {
		this.finishRepayAmount = finishRepayAmount;
	}
	public BigDecimal getUnRepayAmount() {
		return unRepayAmount;
	}
	public void setUnRepayAmount(BigDecimal unRepayAmount) {
		this.unRepayAmount = unRepayAmount;
	}
	public BigDecimal getFinishRepayAmountRate() {
		return finishRepayAmountRate;
	}
	public void setFinishRepayAmountRate(BigDecimal finishRepayAmountRate) {
		this.finishRepayAmountRate = finishRepayAmountRate;
	}
	public BigDecimal getUnRepayAmountRate() {
		return unRepayAmountRate;
	}
	public void setUnRepayAmountRate(BigDecimal unRepayAmountRate) {
		this.unRepayAmountRate = unRepayAmountRate;
	}
	public String getLoanCount() {
		return loanCount;
	}
	public void setLoanCount(String loanCount) {
		this.loanCount = loanCount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}

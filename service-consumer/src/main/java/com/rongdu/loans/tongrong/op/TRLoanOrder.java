package com.rongdu.loans.tongrong.op;

import java.io.Serializable;
import java.math.BigDecimal;

public class TRLoanOrder implements Serializable{

	private static final long serialVersionUID = -42318641500827232L;
	
	/**
	 * 申请日期
	 */
	private String applyDate;
	/**
	 * 借款金额
	 */
	private BigDecimal account;
	/**
	 * 借款期限
	 */
	private Integer loanMonths;
	/**
	 * 借款天数
	 */
	private Integer loanDays;
	/**
	 * 借款用途
	 */
	private String purpose;
	/**
	 * 利率
	 */
	private BigDecimal rate;
	/**
	 * 还款方式
	 */
	private String repayment;
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public Integer getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(Integer loanMonths) {
		this.loanMonths = loanMonths;
	}
	public Integer getLoanDays() {
		return loanDays;
	}
	public void setLoanDays(Integer loanDays) {
		this.loanDays = loanDays;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	
	
}

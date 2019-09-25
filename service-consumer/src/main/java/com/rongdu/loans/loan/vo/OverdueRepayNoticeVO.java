package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 逾期还款推送信息
 * @author likang
 *
 */
public class OverdueRepayNoticeVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 888578684967202780L;

	/**
	 * 标的id
	 */
	private String assetId;
	
//	/**
//	 * 还款金额
//	 */
//	private String repayAmount;
	
	/**
	  * 电子账户
	  */
	private String accountId;
	
//	/**
//	  * 计息开始日
//	  */
//	private String interestStartDate;
//	
//	/**
//	  * 计息结束日（提前还款日期）
//	  */
//	private String interestEndDate;
	
    /**
     * 还款日期 yyyy-MM-dd HH:mm:ss
     */
    private String repayDate;
	
	/**
     * 罚息
     */
    private String overdueInterest;
    /**
     * 逾期管理费
     */
    private String overdueFee;
    /**
     * 减免费用
     */
    private String reduceFee;
    /**
     * 正常利息
     */
    private String interest;

	/**
	 * 本金
	 */
	private String principal;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

//	public String getRepayAmount() {
//		return repayAmount;
//	}
//
//	public void setRepayAmount(String repayAmount) {
//		this.repayAmount = repayAmount;
//	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

//	public String getInterestStartDate() {
//		return interestStartDate;
//	}
//
//	public void setInterestStartDate(String interestStartDate) {
//		this.interestStartDate = interestStartDate;
//	}
//
//	public String getInterestEndDate() {
//		return interestEndDate;
//	}
//
//	public void setInterestEndDate(String interestEndDate) {
//		this.interestEndDate = interestEndDate;
//	}

	public String getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public String getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(String overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getReduceFee() {
		return reduceFee;
	}

	public void setReduceFee(String reduceFee) {
		this.reduceFee = reduceFee;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
}

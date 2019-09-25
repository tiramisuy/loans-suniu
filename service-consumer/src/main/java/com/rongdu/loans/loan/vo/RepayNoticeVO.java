package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 提前还款推送信息
 * @author likang
 *
 */
public class RepayNoticeVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4288209568266654725L;
	
	/**
	 * 标的id
	 */
	private String assetId;
	
	/**
	 * 还款金额
	 */
	private String repayAmount;
	
	/**
	 * 提前还款手续费
	 */
	private String prepayFee;
	
	/**
	  * 电子账户
	  */
	private String accountId;
	
	/**
	  * 计息开始日
	  */
	private String interestStartDate;
	
	/**
	  * 计息结束日（提前还款日期）
	  */
	private String interestEndDate;
	
//	/**
//	 * 利息
//	 */
//	private String interest;
//	
//	/**
//	 * 提前还款服务费
//	 */
//	private String prepayFee;
//	
//	/**
//	 * 本金
//	 */
//	private String principal;
	
	
//	/**
//	 * 实际利率
//	 */
//	private BigDecimal actualRate;
	
//	/**
//	 * 服务费率
//	 */
//	private BigDecimal servFeeRate;

	public String getAssetId() {
		return assetId;
	}

	public String getRepayAmount() {
		return repayAmount;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getInterestStartDate() {
		return interestStartDate;
	}

	public String getInterestEndDate() {
		return interestEndDate;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setInterestStartDate(String interestStartDate) {
		this.interestStartDate = interestStartDate;
	}

	public void setInterestEndDate(String interestEndDate) {
		this.interestEndDate = interestEndDate;
	}

	public String getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(String prepayFee) {
		this.prepayFee = prepayFee;
	}
}

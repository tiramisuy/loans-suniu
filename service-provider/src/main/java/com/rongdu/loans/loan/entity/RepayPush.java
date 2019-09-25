/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 还款推送结果记录Entity
 * @author likang
 * @version 2017-09-08
 */
public class RepayPush extends BaseEntity<RepayPush> {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 9180941389018188554L;
	
	/**
	 * 推送成功
	 */
	public static final int RESULT_SUCCESS = 1;
	
	public static final String SUCCESS = "SUCCESS";
	
	/**
	 * 推送失败
	 */
	public static final int RESULT_FAIL = 2;
	
	/**
	 * 提前还款推送
	 */
	public static final int TYPE_PRE = 1;
	
	/**
	 * 逾期还款推送
	 */
	public static final int TYPE_OVERDUE = 2;
	
	/**
	 * 按期还款推送
	 */
	public static final int TYPE_ONTIME = 3;
	
	/**
	  *用户id
	  */
	private String userId;
	/**
	  *申请编号
	  */
	private String applyId;		
	/**
	  *资产id
	  */
	private String assetId;		
	/**
	  *电子账户
	  */
	private String accountId;		
	/**
	  *推送类型（1-提前还款推送;2-逾期还款推送）
	  */
	private Integer pushType;		
	/**
	  *推送结果（1-成功;2-失败）
	  */
	private Integer pushResult;		
	/**
	  *失败返回信息描述
	  */
	private String pushDescribe;	
	/**
	  *支付公司订单号
	  */
	private String chlOrderNo;	
	/**
	  *交易金额
	  */
	private BigDecimal txAmount;		
	/**
	  *提前还款手续费
	  */
	private BigDecimal prepayFee;		
	/**
	  *本金（提前还款可为空）
	  */
	private BigDecimal principal;		
	/**
	  *利息（提前还款可为空）
	  */
	private BigDecimal interest;		
	/**
	  *减免费用（提前还款可为空）
	  */
	private BigDecimal reduceFee;		
	/**
	  *逾期管理费（提前还款可为空）
	  */
	private BigDecimal overdueFee;		
	/**
	  *逾期罚息（提前还款可为空）
	  */
	private BigDecimal overdueInterest;		
	/**
	  *还款日期 yyyy-MM-dd HH:mm:ss
	  */
	private String repayDate;		
	/**
	  *计息开始日yyyyMMdd（逾期还款可为空）
	  */
	private String interestStartDate;		
	/**
	  *计息结束日yyyyMMdd（逾期还款可为空）
	  */
	private String interestEndDate;		
	
	public RepayPush() {
		super();
	}

	public RepayPush(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}
	
	public Integer getPushResult() {
		return pushResult;
	}

	public void setPushResult(Integer pushResult) {
		this.pushResult = pushResult;
	}
		
	public BigDecimal getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(BigDecimal txAmount) {
		this.txAmount = txAmount;
	}
	
	public BigDecimal getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(BigDecimal prepayFee) {
		this.prepayFee = prepayFee;
	}
	
	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public BigDecimal getReduceFee() {
		return reduceFee;
	}

	public void setReduceFee(BigDecimal reduceFee) {
		this.reduceFee = reduceFee;
	}
	
	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}
	
	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}
	
	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	
	public String getInterestStartDate() {
		return interestStartDate;
	}

	public void setInterestStartDate(String interestStartDate) {
		this.interestStartDate = interestStartDate;
	}
	
	public String getInterestEndDate() {
		return interestEndDate;
	}

	public void setInterestEndDate(String interestEndDate) {
		this.interestEndDate = interestEndDate;
	}

	public String getPushDescribe() {
		return pushDescribe;
	}

	public void setPushDescribe(String pushDescribe) {
		this.pushDescribe = pushDescribe;
	}

	public String getChlOrderNo() {
		return chlOrderNo;
	}

	public void setChlOrderNo(String chlOrderNo) {
		this.chlOrderNo = chlOrderNo;
	}
}

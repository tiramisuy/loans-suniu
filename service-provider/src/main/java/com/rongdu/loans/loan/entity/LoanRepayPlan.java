/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划Entity
 * @author likang
 * @version 2017-06-22
 */
public class LoanRepayPlan extends BaseEntity<LoanRepayPlan> {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2744814435506195827L;
	
	private String applyId;		// 申请流水号
	private String contNo;		// 合同编号
	private String userId;		// 客户号
	private String userName;		// 客户名称
	private String idNo;		// 证件号码
	private String idType;		// 证件类型
	private Date loanStartDate;		// 贷款开始日期
	private Date loanEndDate;		// 贷款终止日期
	private BigDecimal totalAmount;		// 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	private BigDecimal principal;		// 应还本金
	private BigDecimal interest;		// 应还利息
	private BigDecimal servFee;		// 中介服务手续费
	private BigDecimal prepayFee;		// 提前还款手续费
	private BigDecimal penalty;		// 逾期罚息
	private BigDecimal overdueFee;		// 逾期管理费
	private BigDecimal deduction;		// 减免费用
	private Integer totalTerm;		// 贷款期数(月)
	private Integer currentTerm;		// 当前期数
	private Integer payedTerm;		// 已还期数(月)
	private Integer unpayTerm;		// 待还期数(月)
	private BigDecimal payedPrincipal;		// 已还本金
	private BigDecimal unpayPrincipal;		// 待还本金
	private BigDecimal payedInterest;		// 已还利息
	private BigDecimal unpayInterest;		// 待还利息
	private Date curRealRepayDate;    // 当期实际还款日
	private String nextRepayDate;		// 下一期日（或者最后还款日）
	private Integer status;		// 是否已经结清（0-否，1-是）
	private Integer withdrawalSource;	//放款来源：0:线上,1:线下
	private Integer loanStatus;  //借款状态 0 放款；1 取消
	
	
	public Integer getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getApplyId() {
		return applyId;
	}
	public String getContNo() {
		return contNo;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getIdNo() {
		return idNo;
	}
	public String getIdType() {
		return idType;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public Date getLoanEndDate() {
		return loanEndDate;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public BigDecimal getPrincipal() {
		return principal;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public BigDecimal getServFee() {
		return servFee;
	}
	public BigDecimal getPrepayFee() {
		return prepayFee;
	}
	public BigDecimal getPenalty() {
		return penalty;
	}
	public BigDecimal getDeduction() {
		return deduction;
	}
	public Integer getTotalTerm() {
		return totalTerm;
	}
	public Integer getPayedTerm() {
		return payedTerm;
	}
	public Integer getUnpayTerm() {
		return unpayTerm;
	}
	public BigDecimal getPayedPrincipal() {
		return payedPrincipal;
	}
	public BigDecimal getUnpayPrincipal() {
		return unpayPrincipal;
	}
	public BigDecimal getPayedInterest() {
		return payedInterest;
	}
	public BigDecimal getUnpayInterest() {
		return unpayInterest;
	}
	public String getNextRepayDate() {
		return nextRepayDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}
	public void setPrepayFee(BigDecimal prepayFee) {
		this.prepayFee = prepayFee;
	}
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}
	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}
	public void setPayedTerm(Integer payedTerm) {
		this.payedTerm = payedTerm;
	}
	public void setUnpayTerm(Integer unpayTerm) {
		this.unpayTerm = unpayTerm;
	}
	public void setPayedPrincipal(BigDecimal payedPrincipal) {
		this.payedPrincipal = payedPrincipal;
	}
	public void setUnpayPrincipal(BigDecimal unpayPrincipal) {
		this.unpayPrincipal = unpayPrincipal;
	}
	public void setPayedInterest(BigDecimal payedInterest) {
		this.payedInterest = payedInterest;
	}
	public void setUnpayInterest(BigDecimal unpayInterest) {
		this.unpayInterest = unpayInterest;
	}
	public void setNextRepayDate(String nextRepayDate) {
		this.nextRepayDate = nextRepayDate;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCurRealRepayDate() {
		return curRealRepayDate;
	}
	public void setCurRealRepayDate(Date curRealRepayDate) {
		this.curRealRepayDate = curRealRepayDate;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public Integer getCurrentTerm() {
		return currentTerm;
	}

	public void setCurrentTerm(Integer currentTerm) {
		this.currentTerm = currentTerm;
	}
	public Integer getWithdrawalSource() {
		return withdrawalSource;
	}
	public void setWithdrawalSource(Integer withdrawalSource) {
		this.withdrawalSource = withdrawalSource;
	}
	
	
}
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划明细Entity
 * @author likang
 * @version 2017-06-22
 */
public class LoanRepayPlanItem extends BaseEntity<LoanRepayPlanItem> {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6232441672775200062L;
	
	private String applyId;		// 申请编号
	private String contNo;		// 合同编号
	private String userId;		// 客户编号
	private String userName;		// 客户名称
	private Date repayDate;		// 还款日期
	private Integer totalTerm;		// 贷款期数(月)
	private Integer thisTerm;		// 期数
	private BigDecimal totalAmount;		// 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	private BigDecimal principal;		// 应还本金
	private BigDecimal interest;		// 应还利息
	private BigDecimal servFee;		// 中介服务手续费
	private BigDecimal prepayFee;		// 提前还款手续费
	private BigDecimal penalty;		// 逾期罚息
	private BigDecimal deduction;		// 减免费用
	private BigDecimal payedPrincipal;		// 已还本金
	private BigDecimal unpayPrincipal;		// 待还本金
	private BigDecimal payedInterest;		// 已还利息
	private BigDecimal unpayInterest;		// 待还利息
	private String actualRepayDate;		// 实际还款日期
	private Date actualRepayTime;		// 实际还款时间
	private BigDecimal actualRepayAmt;		// 实际还款金额
	private String repayType;		// 还款类型（0-主动还款，1-自动还款）
	private Integer status;		// 是否已经结清（0-否，1-是）
	
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
	public Date getRepayDate() {
		return repayDate;
	}
	public Integer getTotalTerm() {
		return totalTerm;
	}
	public Integer getThisTerm() {
		return thisTerm;
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
	public String getActualRepayDate() {
		return actualRepayDate;
	}
	public Date getActualRepayTime() {
		return actualRepayTime;
	}
	public String getRepayType() {
		return repayType;
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
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}
	public void setThisTerm(Integer thisTerm) {
		this.thisTerm = thisTerm;
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
	public void setActualRepayDate(String actualRepayDate) {
		this.actualRepayDate = actualRepayDate;
	}
	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getActualRepayAmt() {
		return actualRepayAmt;
	}
	public void setActualRepayAmt(BigDecimal actualRepayAmt) {
		this.actualRepayAmt = actualRepayAmt;
	}
}

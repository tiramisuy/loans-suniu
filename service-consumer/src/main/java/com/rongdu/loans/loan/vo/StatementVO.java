package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StatementVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7457048618625029871L;

	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 合同编号
	 */
	private String contNo;
	/**
	 * 客户号
	 */
	private String userId;
	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 贷款开始日期
	 */
	private Date loanStartDate;

	/**
	 * 贷款终止日期
	 */
	private Date loanEndDate;

	/**
	 * 贷款-总共应还本息
	 */
	private BigDecimal loanTotalAmount;

	/**
	 * 贷款-应还本金
	 */
	private BigDecimal loanPrincipal;

	/**
	 * 贷款-应还利息
	 */
	private BigDecimal loanInterest;

	/**
	 * 贷款-总共中介服务手续费
	 */
	private BigDecimal loanServFee;

	/**
	 * 贷款-总共提前还款手续费
	 */
	private BigDecimal loanPrepayFee;

	/**
	 * 贷款-总共逾期罚息
	 */
	private BigDecimal loanPenalty;

	/**
	 * 贷款-总共逾期管理费
	 */
	private BigDecimal loanOverdueFee;

	/**
	 * 贷款-总共减免费用
	 */
	private BigDecimal loanDeduction;

	/**
	 * 贷款-总期数
	 */
	private Integer loanTotalTerm;

	/**
	 * 贷款-已还期数
	 */
	private Integer loanPayedTerm;

	/**
	 * 贷款-未还期数
	 */
	private Integer loanUnpayTerm;

	/**
	 * 贷款-已还本金
	 */
	private BigDecimal loanPayedPrincipal;

	/**
	 * 贷款-未还本金
	 */
	private BigDecimal loanUnpayPrincipal;

	/**
	 * 贷款-已还利息
	 */
	private BigDecimal loanPayedInterest;

	/**
	 * 贷款-未还利息
	 */
	private BigDecimal loanUnpayInterest;

	/**
	 * 当前期-应还本息
	 */
	private BigDecimal curTotalAmount;

	/**
	 * 当前期-应还本金
	 */
	private BigDecimal curPrincipal;

	/**
	 * 当前期-应还利息
	 */
	private BigDecimal curInterest;

	/**
	 * 当前期-中介服务手续费
	 */
	private BigDecimal curServFee;

	/**
	 * 当前期-提前还款手续费
	 */
	private BigDecimal curPrepayFee;

	/**
	 * 当前期-逾期罚息
	 */
	private BigDecimal curPenalty;

	/**
	 * 当前期-总共逾期管理费
	 */
	private BigDecimal curOverdueFee;

	/**
	 * 当前期-共减免费用
	 */
	private BigDecimal curDeduction;

	/**
	 * 当前期-期数
	 */
	private Integer curTerm;

	/**
	 * 当前期-还款日期
	 */
	private Date curRepayDate;

	/**
	 * 当前期-还款明细id
	 */
	private String curRepayItemId;

	/**
	 * 当前期-还款状态
	 */
	private Integer curRepayStatus;

	/**
	 * 当前实还金额
	 */
	private BigDecimal curActualRepayAmt;

	public BigDecimal getLoanTotalAmount() {
		return loanTotalAmount;
	}

	public BigDecimal getLoanPrincipal() {
		return loanPrincipal;
	}

	public BigDecimal getLoanInterest() {
		return loanInterest;
	}

	public BigDecimal getLoanServFee() {
		return loanServFee;
	}

	public BigDecimal getLoanPrepayFee() {
		return loanPrepayFee;
	}

	public BigDecimal getLoanPenalty() {
		return loanPenalty;
	}

	public BigDecimal getLoanDeduction() {
		return loanDeduction;
	}

	public BigDecimal getCurTotalAmount() {
		return curTotalAmount;
	}

	public BigDecimal getCurPrincipal() {
		return curPrincipal;
	}

	public BigDecimal getCurInterest() {
		return curInterest;
	}

	public BigDecimal getCurServFee() {
		return curServFee;
	}

	public BigDecimal getCurPrepayFee() {
		return curPrepayFee;
	}

	public BigDecimal getCurPenalty() {
		return curPenalty;
	}

	public BigDecimal getCurDeduction() {
		return curDeduction;
	}

	public void setLoanTotalAmount(BigDecimal loanTotalAmount) {
		this.loanTotalAmount = loanTotalAmount;
	}

	public void setLoanPrincipal(BigDecimal loanPrincipal) {
		this.loanPrincipal = loanPrincipal;
	}

	public void setLoanInterest(BigDecimal loanInterest) {
		this.loanInterest = loanInterest;
	}

	public void setLoanServFee(BigDecimal loanServFee) {
		this.loanServFee = loanServFee;
	}

	public void setLoanPrepayFee(BigDecimal loanPrepayFee) {
		this.loanPrepayFee = loanPrepayFee;
	}

	public void setLoanPenalty(BigDecimal loanPenalty) {
		this.loanPenalty = loanPenalty;
	}

	public void setLoanDeduction(BigDecimal loanDeduction) {
		this.loanDeduction = loanDeduction;
	}

	public void setCurTotalAmount(BigDecimal curTotalAmount) {
		this.curTotalAmount = curTotalAmount;
	}

	public void setCurPrincipal(BigDecimal curPrincipal) {
		this.curPrincipal = curPrincipal;
	}

	public void setCurInterest(BigDecimal curInterest) {
		this.curInterest = curInterest;
	}

	public void setCurServFee(BigDecimal curServFee) {
		this.curServFee = curServFee;
	}

	public void setCurPrepayFee(BigDecimal curPrepayFee) {
		this.curPrepayFee = curPrepayFee;
	}

	public void setCurPenalty(BigDecimal curPenalty) {
		this.curPenalty = curPenalty;
	}

	public void setCurDeduction(BigDecimal curDeduction) {
		this.curDeduction = curDeduction;
	}

	public Integer getLoanTotalTerm() {
		return loanTotalTerm;
	}

	public Integer getLoanPayedTerm() {
		return loanPayedTerm;
	}

	public Integer getLoanUnpayTerm() {
		return loanUnpayTerm;
	}

	public BigDecimal getLoanPayedPrincipal() {
		return loanPayedPrincipal;
	}

	public BigDecimal getLoanUnpayPrincipal() {
		return loanUnpayPrincipal;
	}

	public BigDecimal getLoanPayedInterest() {
		return loanPayedInterest;
	}

	public BigDecimal getLoanUnpayInterest() {
		return loanUnpayInterest;
	}

	public Integer getCurTerm() {
		return curTerm;
	}

	public void setLoanTotalTerm(Integer loanTotalTerm) {
		this.loanTotalTerm = loanTotalTerm;
	}

	public void setLoanPayedTerm(Integer loanPayedTerm) {
		this.loanPayedTerm = loanPayedTerm;
	}

	public void setLoanUnpayTerm(Integer loanUnpayTerm) {
		this.loanUnpayTerm = loanUnpayTerm;
	}

	public void setLoanPayedPrincipal(BigDecimal loanPayedPrincipal) {
		this.loanPayedPrincipal = loanPayedPrincipal;
	}

	public void setLoanUnpayPrincipal(BigDecimal loanUnpayPrincipal) {
		this.loanUnpayPrincipal = loanUnpayPrincipal;
	}

	public void setLoanPayedInterest(BigDecimal loanPayedInterest) {
		this.loanPayedInterest = loanPayedInterest;
	}

	public void setLoanUnpayInterest(BigDecimal loanUnpayInterest) {
		this.loanUnpayInterest = loanUnpayInterest;
	}

	public void setCurTerm(Integer curTerm) {
		this.curTerm = curTerm;
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

	public String getIdNo() {
		return idNo;
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

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public BigDecimal getLoanOverdueFee() {
		return loanOverdueFee;
	}

	public BigDecimal getCurOverdueFee() {
		return curOverdueFee;
	}

	public void setLoanOverdueFee(BigDecimal loanOverdueFee) {
		this.loanOverdueFee = loanOverdueFee;
	}

	public void setCurOverdueFee(BigDecimal curOverdueFee) {
		this.curOverdueFee = curOverdueFee;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public Date getCurRepayDate() {
		return curRepayDate;
	}

	public Integer getCurRepayStatus() {
		return curRepayStatus;
	}

	public void setCurRepayDate(Date curRepayDate) {
		this.curRepayDate = curRepayDate;
	}

	public void setCurRepayStatus(Integer curRepayStatus) {
		this.curRepayStatus = curRepayStatus;
	}

	public String getCurRepayItemId() {
		return curRepayItemId;
	}

	public void setCurRepayItemId(String curRepayItemId) {
		this.curRepayItemId = curRepayItemId;
	}

	public BigDecimal getCurActualRepayAmt() {
		return curActualRepayAmt;
	}

	public void setCurActualRepayAmt(BigDecimal curActualRepayAmt) {
		this.curActualRepayAmt = curActualRepayAmt;
	}

}

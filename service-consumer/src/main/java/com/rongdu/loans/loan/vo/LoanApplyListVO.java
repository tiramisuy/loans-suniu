package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 贷款申请简单VO
 * @author likang
 *
 */
public class LoanApplyListVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1173151302538488485L;
	
	private String applyId;     // 申请编号
	private Date applyTime;	// 申请时间
	private String userName;	// 客户名称
	private String idNo;		// 证件号码
	private String mobile;		// 手机号码
	private BigDecimal approveAmt;  // 贷款审批金额
	private Integer approveTerm;// 审批期限(按天)
	private Integer stage;		// 贷款处理阶段（0-初始，1-贷款申请，2-大数据风控，3-人工审批，4-签订，5-放款，6-还款，7-逾期，8-核销）
	private Integer applyStatus; // 审批状态（0-初始，2-8-通过，9-拒绝）
	
	private boolean isHaveLoan; // 当前在本平台是否有贷款
	
	private BigDecimal interest;	// 当前利息
	private String repayDate;   // 还款日期
	private String loanDate;    // 放款日期
	private BigDecimal penalty;     // 逾期罚息
	private BigDecimal deduction;   // 减免费用
	
	private boolean isOverdue;    // 当前是否逾期
	private Integer overdueDays; // 当前期逾期天数
	
	private String cashState; // 提现状态 [0]-未到账; [1]-可提现;
	private BigDecimal cashAmt;   // 可提现金额
	private Date toAccounttime; // 到款到账（恒丰银行电子账户）时间
	
	public String getApplyId() {
		return applyId;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public String getUserName() {
		return userName;
	}
	public String getIdNo() {
		return idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public BigDecimal getApproveAmt() {
		return approveAmt;
	}
	public Integer getApproveTerm() {
		return approveTerm;
	}
	public boolean isHaveLoan() {
		return isHaveLoan;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}
	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}
	public void setHaveLoan(boolean isHaveLoan) {
		this.isHaveLoan = isHaveLoan;
		if(!isHaveLoan) {
			setOverdue(false);
		}
	}
	public String getRepayDate() {
		return repayDate;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public BigDecimal getPenalty() {
		return penalty;
	}
	public BigDecimal getDeduction() {
		return deduction;
	}
	public String getCashState() {
		return cashState;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public Date getToAccounttime() {
		return toAccounttime;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}
	public void setCashState(String cashState) {
		this.cashState = cashState;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public void setToAccounttime(Date toAccounttime) {
		this.toAccounttime = toAccounttime;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public Integer getStage() {
		return stage;
	}
	public Integer getApplyStatus() {
		return applyStatus;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	public Integer getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	public boolean isOverdue() {
		return isOverdue;
	}
	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}
}

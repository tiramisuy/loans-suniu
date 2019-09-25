package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款申请摘要
 * 
 * @author likang
 * 
 */
public class LoansApplySummaryVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5531578751371649068L;

	private String applyId; // 申请编号
	private BigDecimal approveAmt; // 贷款审批金额
	private Integer approveTerm;// 审批期限(按天)
	private String applyTime; // 申请日期
	private Integer overType; // 清算方式 611- 提前， 612-正常， 712-逾期
	private Integer repayMethod; // 还款方式
	private Integer term; // 借款期数
	private Integer applyStatus; // 结清状态
	private String productName;// 产品名称 code y0601
	private Integer applyTerm;// 申请期限

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}

	public Integer getApproveTerm() {
		return approveTerm;
	}

	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getOverType() {
		return overType;
	}

	public void setOverType(Integer overType) {
		this.overType = overType;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

}

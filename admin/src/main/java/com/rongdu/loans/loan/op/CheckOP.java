package com.rongdu.loans.loan.op;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提交贷款申请参数对象
 * 
 * @author likang
 * 
 */
public class CheckOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1005646992743628884L;
	/**
	 * 审核结果，1通过，2不通过
	 */
	@NotNull(message = "审核结果不能为空")
	private Integer checkResult;
	/**
	 * 不通过的原因
	 */
	private String refuseId;
	/**
	 * 审批意见，备注
	 */
	private String remark;
	/**
	 * 申请编号
	 */
	@NotNull(message = "申请编号不能为空")
	private String applyId;

	/**
	 * 审批金额
	 */
	private BigDecimal approveAmt;
	/**
	 * 审批期限
	 */
	private Integer approveTerm;
	/**
	 * 还款期数
	 */
	private Integer repayTerm;
	/**
	 * 标的类型
	 */
	private Integer borrowType;
	/**
	 * 服务费率
	 */
	private BigDecimal servFeeRate;

	/**
	 * 产品
	 */
	private String productId;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public String getRefuseId() {
		return refuseId;
	}

	public void setRefuseId(String refuseId) {
		this.refuseId = refuseId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Integer repayTerm) {
		this.repayTerm = repayTerm;
	}

	public Integer getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}

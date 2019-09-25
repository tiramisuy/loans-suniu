package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 提交贷款申请参数对象
 * 
 * @author likang
 * 
 */
public class LoanCheckOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1005646992743628884L;
	/**
	 * 审核结果，1通过，2不通过, 3人工审核
	 */
	@NotNull(message = "审核结果不能为空")
	private Integer checkResult;
	/**
	 * 拒绝原因ID
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
	 * 操作员IP地址
	 */
	private String ip;
	/**
	 * 操作人员ID
	 */
	private String operatorId;
	/**
	 * 操作人员姓名
	 */
	private String operatorName;
	/**
	 * 审批金额
	 */
	@NotNull(message = "审批金额不能为空")
	private BigDecimal approveAmt;

	/**
	 * 审批期限
	 */
	@NotNull(message = "审批期限不能为空")
	private Integer approveTerm;

	/**
	 * 还款期数
	 */
	@NotNull(message = "还款期数不能为空")
	private Integer repayTerm;

	/**
	 * 标的类型
	 */
	@NotNull(message = "标的类型不能为空")
	private Integer borrowType;
	/**
	 * 服务费率
	 */
	@NotNull(message = "服务费率不能为空")
	private BigDecimal servFeeRate;

	private Integer derate;

	/**
	 * 信审级别，默认不是初级
	 */
	private boolean level = false;

	@NotNull(message = "产品不能为空")
	private String productId;

	private Integer autoApproveStatus2;// 二次机审状态（0：拒绝，1：通过）

	public boolean isLevel() {
		return level;
	}

	public void setLevel(boolean level) {
		this.level = level;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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

	public Integer getDerate() {
		return derate;
	}

	public void setDerate(Integer derate) {
		this.derate = derate;
	}

	public Integer getAutoApproveStatus2() {
		return autoApproveStatus2;
	}

	public void setAutoApproveStatus2(Integer autoApproveStatus2) {
		this.autoApproveStatus2 = autoApproveStatus2;
	}
}

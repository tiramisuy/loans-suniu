/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 减免操作日志Entity
 * @author zhangxiaolong
 * @version 2017-07-27
 */
public class DeductionLog extends BaseEntity<DeductionLog> {

	private static final long serialVersionUID = 1L;
	/**
	 *用户ID
	 */
	private String userId;
	/**
	 *贷款申请单ID
	 */
	private String applyId;
	/**
	 *还款明细ID
	 */
	private String repayPlanItemId;
	/**
	 *减免费用
	 */
	private BigDecimal deduction;
	/**
	 *来源（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	 */
	private Integer source;
	/**
	 *终审人ID
	 */
	private String approverId;
	/**
	 *终审人姓名
	 */
	private String approverName;
	/**
	 *处理时间
	 */
	private Date approveTime;
	/**
	 *状态: 0待审核，1通过，2不通过
	 */
	private Integer status;

	public DeductionLog() {
		super();
	}

	public DeductionLog(String id){
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

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
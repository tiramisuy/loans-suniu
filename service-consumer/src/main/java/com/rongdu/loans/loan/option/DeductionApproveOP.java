/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 减免操作日志
 * @author zhangxiaolong
 * @version 2017-07-27
 */
public class DeductionApproveOP implements Serializable {

	/**
	 * 记录id
	 */
	@NotNull(message = "减免操作ID不能为空")
	private String id;
	/**
	 *还款明细ID
	 */
	@NotNull(message = "还款明细ID不能为空")
	private String repayPlanItemId;
	/**
	 *终审人ID
	 */
	private String approverId;
	/**
	 *终审人姓名
	 */
	private String approverName;
	/**
	 *状态: 0待审核，1通过，2不通过
	 */
	@NotNull(message = "减免状态不能为空")
	@Min(value = 1)
	@Max(value = 2)
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
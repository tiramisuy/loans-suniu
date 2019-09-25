/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 减免操作日志
 * @author zhangxiaolong
 * @version 2017-07-27
 */
public class DeductionApplyOP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	  *还款明细ID
	  */
	@NotNull(message = "还款明细ID不能为空")
	private String repayPlanItemId;
	/**
	 * 减免费用
	 */
	@NotNull(message = "减免费用不能为空")
	private BigDecimal deduction;
	/**
	 * 减免理由
	 */
	private String remark;
	/**
	 * 来源
	 */
	private Integer source;
	/**
	 * 	创建者
	 */
	protected String createBy;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
}
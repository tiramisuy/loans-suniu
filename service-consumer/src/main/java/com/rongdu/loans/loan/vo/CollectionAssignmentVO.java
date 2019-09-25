/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 催收分配记录Entity
 * @author zhangxiaolong
 * @version 2017-09-28
 */
public class CollectionAssignmentVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	  *还款明细ID
	  */
	private String repayPlanItemId;
	/**
	  *申请编号
	  */
	private String applyId;
	/**
	  *合同编号
	  */
	private String contNo;
	/**
	  *客户编号
	  */
	private String userId;
	/**
	  *客户名称
	  */
	private String userName;
	/**
	 *催收人员id
	 */
	private String fromOperatorId;
	/**
	 *催收人员姓名
	 */
	private String fromOperatorName;
	/**
	 *催收人员id
	 */
	private String toOperatorId;
	/**
	 *催收人员姓名
	 */
	private String toOperatorName;
	/**
	  *退回时间
	  */
	private Date returnTime;
	/**
	  *退回方式：1.定时退回，2.不退回
	  */
	private Integer returnType;
	/**
	 * 	备注
	 */
	private String remark;
	/**
	 * 	创建日期
	 */
	private Date createTime;
	/**
	 * 	创建者userId
	 */
	private String createBy;


	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromOperatorId() {
		return fromOperatorId;
	}

	public void setFromOperatorId(String fromOperatorId) {
		this.fromOperatorId = fromOperatorId;
	}

	public String getFromOperatorName() {
		return fromOperatorName;
	}

	public void setFromOperatorName(String fromOperatorName) {
		this.fromOperatorName = fromOperatorName;
	}

	public String getToOperatorId() {
		return toOperatorId;
	}

	public void setToOperatorId(String toOperatorId) {
		this.toOperatorId = toOperatorId;
	}

	public String getToOperatorName() {
		return toOperatorName;
	}

	public void setToOperatorName(String toOperatorName) {
		this.toOperatorName = toOperatorName;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	public Integer getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
}
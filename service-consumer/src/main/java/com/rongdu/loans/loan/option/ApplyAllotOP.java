/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 贷款订单分配表Entity
 * @author liuliang
 * @version 2018-07-12
 */
public class ApplyAllotOP extends BaseEntity<ApplyAllotOP> {
	
	private static final long serialVersionUID = 1L;
	
	private String id; // 申请编号
	
	private String contNo; // 贷款合同号
	/*
	  *贷款状态
	  */
	private Integer status;		
	
	private String statusStr;
	/**
	  *申请单状态（0-未完结，1-已完结，2-初始）
	  */
	private Integer applyStatus;		
	/**
	  *审核结果：1，自动审核通过，2，自动审核不通过，3，人工审核通过，4，人工审核不通过
	  */
	private Integer approveResult;		
	/**
	  *审核时间
	  */
	private Date approveTime;		
	
	
	private Integer stage; // 贷款处理阶段（0-初始，1-贷款申请，2-大数据风控，3-人工审批，4-签订，5-放款，6-还款，7-逾期，8-核销）
	

	/**
	 * 	备注
	 */
	protected String remark;
	
	
	private String approverId; // 终审人ID
	private String approverName; // 终审人姓名
	/**
	 * 	最后修改者userId
	 */
	protected String updateBy;
	/**
	 *  更新日期
	 */
	protected Date updateTime;	
	
	/**
	  *商户公司id
	  */
	private String companyId;	
	
	private int del = 0;
	
	
	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
	
	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getStage() {
		return stage;
	}


	public void setStage(Integer stage) {
		this.stage = stage;
	}


	public ApplyAllotOP() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}
	
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	


	public String getStatusStr() {
		return statusStr;
	}


	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}


	public String getContNo() {
		return contNo;
	}


	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	
}
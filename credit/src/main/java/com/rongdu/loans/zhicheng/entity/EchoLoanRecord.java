/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-借款记录历史Entity
 * @author sunda
 * @version 2017-08-14
 */
public class EchoLoanRecord extends BaseEntity<EchoLoanRecord> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *提供数据的机构代号
	  */
	private String orgName;		
	/**
	  *被查询借款人姓名
	  */
	private String name;		
	/**
	  *被查询借款人身份证号
	  */
	private String certNo;		
	/**
	  *借款时间
	  */
	private String loanDate;		
	/**
	  *期数
	  */
	private Integer periods;		
	/**
	  *借款金额
	  */
	private String loanAmount;		
	/**
	  *审批结果码
	  */
	private String approvalStatusCode;		
	/**
	  *还款状态码
	  */
	private String loanStatusCode;		
	/**
	  *借款类型码
	  */
	private String loanTypeCode;		
	/**
	  *逾期金额
	  */
	private String overdueAmount;		
	/**
	  *逾期情况
	  */
	private String overdueStatus;		
	/**
	  *历史逾期总次
	  */
	private Integer overdueTotal;		
	/**
	  *历史逾期 M3+次数（不含M3，包括 M6及以上）
	  */
	private Integer overdueM3;		
	/**
	  *历史逾期 M6+次数（不含M6）
	  */
	private Integer overdueM6;		
	/**
	  *流水号
	  */
	private String flowId;		
	
	public EchoLoanRecord() {
		super();
	}

	public EchoLoanRecord(String id){
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
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	
	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	
	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public String getApprovalStatusCode() {
		return approvalStatusCode;
	}

	public void setApprovalStatusCode(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
	}
	
	public String getLoanStatusCode() {
		return loanStatusCode;
	}

	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}
	
	public String getLoanTypeCode() {
		return loanTypeCode;
	}

	public void setLoanTypeCode(String loanTypeCode) {
		this.loanTypeCode = loanTypeCode;
	}
	
	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	
	public String getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
	}
	
	public Integer getOverdueTotal() {
		return overdueTotal;
	}

	public void setOverdueTotal(Integer overdueTotal) {
		this.overdueTotal = overdueTotal;
	}
	
	public Integer getOverdueM3() {
		return overdueM3;
	}

	public void setOverdueM3(Integer overdueM3) {
		this.overdueM3 = overdueM3;
	}
	
	public Integer getOverdueM6() {
		return overdueM6;
	}

	public void setOverdueM6(Integer overdueM6) {
		this.overdueM6 = overdueM6;
	}
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
}
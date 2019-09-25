/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-高风险名单Entity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportHighRiskList extends BaseEntity<ReportHighRiskList> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *报告ID
	  */
	private String reportId;		
	/**
	  *风险级别(黑/灰名单)
	  */
	private String riskGrade;		
	/**
	  *击中方式（身份证加姓名/身份证/手机号）
	  */
	private String riskIdType;		
	/**
	  *第一级分类
	  */
	private String firstType;		
	/**
	  *第二级分类
	  */
	private String secondType;		
	
	public ReportHighRiskList() {
		super();
	}

	public ReportHighRiskList(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	public String getRiskGrade() {
		return riskGrade;
	}

	public void setRiskGrade(String riskGrade) {
		this.riskGrade = riskGrade;
	}
	
	public String getRiskIdType() {
		return riskIdType;
	}

	public void setRiskIdType(String riskIdType) {
		this.riskIdType = riskIdType;
	}
	
	public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}
	
	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}
	
}
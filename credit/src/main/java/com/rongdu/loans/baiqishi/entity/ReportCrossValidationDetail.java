/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-交叉信息验证明细Entity
 * @author sunda
 * @version 2017-08-22
 */
public class ReportCrossValidationDetail extends BaseEntity<ReportCrossValidationDetail> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String user;		
	/**
	  *报告ID
	  */
	private String reportId;		
	/**
	  *验证项代码
	  */
	private String inspectionItemsCode;		
	/**
	  *验证项
	  */
	private String inspectionItems;		
	/**
	  *证据
	  */
	private String evidence;		
	/**
	  *取值
	  */
	private String result;		
	
	public ReportCrossValidationDetail() {
		super();
	}

	public ReportCrossValidationDetail(String id){
		super(id);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	public String getInspectionItemsCode() {
		return inspectionItemsCode;
	}

	public void setInspectionItemsCode(String inspectionItemsCode) {
		this.inspectionItemsCode = inspectionItemsCode;
	}
	
	public String getInspectionItems() {
		return inspectionItems;
	}

	public void setInspectionItems(String inspectionItems) {
		this.inspectionItems = inspectionItems;
	}
	
	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
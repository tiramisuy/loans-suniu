/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-反欺诈云Entity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportAntiFraudCloud extends BaseEntity<ReportAntiFraudCloud> {
	
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
	  *90天之内是否多头申请
	  */
	private String partnerCount;		
	/**
	  *手机号关联身份证个数
	  */
	private String idcCount;		
	/**
	  *身份证关联手机号个数
	  */
	private String phoneCount;		
	/**
	  *手机号星网模型大小
	  */
	private String starnetCount;		
	
	public ReportAntiFraudCloud() {
		super();
	}

	public ReportAntiFraudCloud(String id){
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
	
	public String getPartnerCount() {
		return partnerCount;
	}

	public void setPartnerCount(String partnerCount) {
		this.partnerCount = partnerCount;
	}
	
	public String getIdcCount() {
		return idcCount;
	}

	public void setIdcCount(String idcCount) {
		this.idcCount = idcCount;
	}
	
	public String getPhoneCount() {
		return phoneCount;
	}

	public void setPhoneCount(String phoneCount) {
		this.phoneCount = phoneCount;
	}
	
	public String getStarnetCount() {
		return starnetCount;
	}

	public void setStarnetCount(String starnetCount) {
		this.starnetCount = starnetCount;
	}
	
}
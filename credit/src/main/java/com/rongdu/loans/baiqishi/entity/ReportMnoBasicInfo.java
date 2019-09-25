/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-运营商基本信息-baseInfoEntity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportMnoBasicInfo extends BaseEntity<ReportMnoBasicInfo> {
	
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
	  *号码
	  */
	private String mobile;		
	/**
	  *运营商类型(移动、联通、电信)
	  */
	private String monType;		
	/**
	  *归属地
	  */
	private String belongTo;		
	/**
	  *绑定身份证
	  */
	private String boundCertNo;		
	/**
	  *绑定姓名
	  */
	private String boundName;		
	/**
	  *绑定email
	  */
	private String boundEmail;		
	
	public ReportMnoBasicInfo() {
		super();
	}

	public ReportMnoBasicInfo(String id){
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
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getMonType() {
		return monType;
	}

	public void setMonType(String monType) {
		this.monType = monType;
	}
	
	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	
	public String getBoundCertNo() {
		return boundCertNo;
	}

	public void setBoundCertNo(String boundCertNo) {
		this.boundCertNo = boundCertNo;
	}
	
	public String getBoundName() {
		return boundName;
	}

	public void setBoundName(String boundName) {
		this.boundName = boundName;
	}
	
	public String getBoundEmail() {
		return boundEmail;
	}

	public void setBoundEmail(String boundEmail) {
		this.boundEmail = boundEmail;
	}
	
}
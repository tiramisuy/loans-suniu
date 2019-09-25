/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-紧急联系人信息Entity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportEmergencyContact extends BaseEntity<ReportEmergencyContact> {
	
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
	  *联系人姓名
	  */
	private String name;		
	/**
	  *联系人与申请人关系
	  */
	private String relation;		
	/**
	  *联系人电话
	  */
	private String mobile;		
	/**
	  *联系人电话归属地
	  */
	private String belongTo;		
	/**
	  *最近一次联系时间
	  */
	private String latestConnectTime;		
	/**
	  *最早一次联系时间
	  */
	private String firstConnectTime;		
	/**
	  *近3天联系次数
	  */
	private String threeDaysConnectCount;		
	/**
	  *近3天联系时长（单位：秒）
	  */
	private String threeDaysConnectDuration;		
	/**
	  *近7天联系次数
	  */
	private String sevenDaysConnectCount;		
	/**
	  *近7天联系时长（单位：秒）
	  */
	private String sevenDaysConnectDuration;		
	/**
	  *近30天联系次数
	  */
	private String thirtyDaysConnectCount;		
	/**
	  *近30天联系时长（单位：秒）
	  */
	private String thirtyDaysConnectDuration;		
	/**
	  *近半年联系次数
	  */
	private String connectCount;		
	/**
	  *近半年联系时长（单位：秒）
	  */
	private String connectDuration;		
	
	public ReportEmergencyContact() {
		super();
	}

	public ReportEmergencyContact(String id){
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	
	public String getLatestConnectTime() {
		return latestConnectTime;
	}

	public void setLatestConnectTime(String latestConnectTime) {
		this.latestConnectTime = latestConnectTime;
	}
	
	public String getFirstConnectTime() {
		return firstConnectTime;
	}

	public void setFirstConnectTime(String firstConnectTime) {
		this.firstConnectTime = firstConnectTime;
	}
	
	public String getThreeDaysConnectCount() {
		return threeDaysConnectCount;
	}

	public void setThreeDaysConnectCount(String threeDaysConnectCount) {
		this.threeDaysConnectCount = threeDaysConnectCount;
	}
	
	public String getThreeDaysConnectDuration() {
		return threeDaysConnectDuration;
	}

	public void setThreeDaysConnectDuration(String threeDaysConnectDuration) {
		this.threeDaysConnectDuration = threeDaysConnectDuration;
	}
	
	public String getSevenDaysConnectCount() {
		return sevenDaysConnectCount;
	}

	public void setSevenDaysConnectCount(String sevenDaysConnectCount) {
		this.sevenDaysConnectCount = sevenDaysConnectCount;
	}
	
	public String getSevenDaysConnectDuration() {
		return sevenDaysConnectDuration;
	}

	public void setSevenDaysConnectDuration(String sevenDaysConnectDuration) {
		this.sevenDaysConnectDuration = sevenDaysConnectDuration;
	}
	
	public String getThirtyDaysConnectCount() {
		return thirtyDaysConnectCount;
	}

	public void setThirtyDaysConnectCount(String thirtyDaysConnectCount) {
		this.thirtyDaysConnectCount = thirtyDaysConnectCount;
	}
	
	public String getThirtyDaysConnectDuration() {
		return thirtyDaysConnectDuration;
	}

	public void setThirtyDaysConnectDuration(String thirtyDaysConnectDuration) {
		this.thirtyDaysConnectDuration = thirtyDaysConnectDuration;
	}
	
	public String getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(String connectCount) {
		this.connectCount = connectCount;
	}
	
	public String getConnectDuration() {
		return connectDuration;
	}

	public void setConnectDuration(String connectDuration) {
		this.connectDuration = connectDuration;
	}
	
}
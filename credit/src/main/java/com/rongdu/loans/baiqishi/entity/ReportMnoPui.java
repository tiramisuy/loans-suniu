/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-运营商分时间段统计数据-mnoPeriodUsedInfosEntity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportMnoPui extends BaseEntity<ReportMnoPui> {
	
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
	  *时间段类型（00:00 ~ 05:59, 06:00 ~ 11:59, 12:00 ~ 17:59, 18:00 ~ 23:59）
	  */
	private String periodType;		
	/**
	  *运营商类型(中国移动、中国联通、中国电信)
	  */
	private String mnoType;		
	/**
	  *手机号归属地
	  */
	private String belongTo;		
	/**
	  *主叫次数
	  */
	private Integer originatingCallCount;		
	/**
	  *主叫时间(秒)
	  */
	private Integer originatingCallTime;		
	/**
	  *被叫次数
	  */
	private Integer terminatingCallCount;		
	/**
	  *被叫时间（秒）
	  */
	private Integer terminatingCallTime;		
	/**
	  *短信数量
	  */
	private Integer msgCount;		
	
	public ReportMnoPui() {
		super();
	}

	public ReportMnoPui(String id){
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
	
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	public String getMnoType() {
		return mnoType;
	}

	public void setMnoType(String mnoType) {
		this.mnoType = mnoType;
	}
	
	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	
	public Integer getOriginatingCallCount() {
		return originatingCallCount;
	}

	public void setOriginatingCallCount(Integer originatingCallCount) {
		this.originatingCallCount = originatingCallCount;
	}
	
	public Integer getOriginatingCallTime() {
		return originatingCallTime;
	}

	public void setOriginatingCallTime(Integer originatingCallTime) {
		this.originatingCallTime = originatingCallTime;
	}
	
	public Integer getTerminatingCallCount() {
		return terminatingCallCount;
	}

	public void setTerminatingCallCount(Integer terminatingCallCount) {
		this.terminatingCallCount = terminatingCallCount;
	}
	
	public Integer getTerminatingCallTime() {
		return terminatingCallTime;
	}

	public void setTerminatingCallTime(Integer terminatingCallTime) {
		this.terminatingCallTime = terminatingCallTime;
	}
	
	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}
	
}
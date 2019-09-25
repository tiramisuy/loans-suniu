/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-本人通话活动地区-mnoCommonlyConnectAreasEntity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportMnoCca extends BaseEntity<ReportMnoCca> {
	
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
	  *地区
	  */
	private String area;		
	/**
	  *通话号码个数
	  */
	private Integer numberCount;		
	/**
	  *呼出时间(秒)
	  */
	private Integer originatingCallCount;		
	/**
	  *绑定身份证
	  */
	private Integer originatingCallTime;		
	/**
	  *呼出时间(秒)
	  */
	private Integer terminatingCallCount;		
	/**
	  *呼入时间(秒)
	  */
	private Integer terminatingCallTime;		
	/**
	  *呼入次数百分比
	  */
	private String callInCountPercentage;		
	/**
	  *呼入时间百分比
	  */
	private String callInTimePercentage;		
	/**
	  *呼出次数百分比
	  */
	private String callOutCountPercentage;		
	/**
	  *呼出时间百分比
	  */
	private String callOutTimePercentage;		
	
	public ReportMnoCca() {
		super();
	}

	public ReportMnoCca(String id){
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
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getNumberCount() {
		return numberCount;
	}

	public void setNumberCount(Integer numberCount) {
		this.numberCount = numberCount;
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
	
	public String getCallInCountPercentage() {
		return callInCountPercentage;
	}

	public void setCallInCountPercentage(String callInCountPercentage) {
		this.callInCountPercentage = callInCountPercentage;
	}
	
	public String getCallInTimePercentage() {
		return callInTimePercentage;
	}

	public void setCallInTimePercentage(String callInTimePercentage) {
		this.callInTimePercentage = callInTimePercentage;
	}
	
	public String getCallOutCountPercentage() {
		return callOutCountPercentage;
	}

	public void setCallOutCountPercentage(String callOutCountPercentage) {
		this.callOutCountPercentage = callOutCountPercentage;
	}
	
	public String getCallOutTimePercentage() {
		return callOutTimePercentage;
	}

	public void setCallOutTimePercentage(String callOutTimePercentage) {
		this.callOutTimePercentage = callOutTimePercentage;
	}
	
}
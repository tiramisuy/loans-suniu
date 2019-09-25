/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-出行数据Entity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportGoOutData extends BaseEntity<ReportGoOutData> {
	
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
	  *出发地
	  */
	private String departure;		
	/**
	  *目的地
	  */
	private String destination;		
	/**
	  *出发时间
	  */
	private String beginTime;		
	/**
	  *回程时间
	  */
	private String endTime;		
	/**
	  *时间段
	  */
	private String period;		
	
	public ReportGoOutData() {
		super();
	}

	public ReportGoOutData(String id){
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
	
	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
}
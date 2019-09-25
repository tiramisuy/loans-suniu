/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-常用联系电话(近6个月)mnoCommonlyConnectMobilesEntity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportMnoCcm extends BaseEntity<ReportMnoCcm> {
	
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
	  *开始时间(时间戳类型)
	  */
	private String beginTime;		
	/**
	  *最后时间(时间戳类型)
	  */
	private String endTime;		
	/**
	  *运营商类型(中国移动、中国联通、中国电信)
	  */
	private String monType;		
	/**
	  *归属地
	  */
	private String belongTo;		
	/**
	  *联系次数
	  */
	private Integer connectCount;		
	/**
	  *联系时间（秒）
	  */
	private Integer connectTime;		
	/**
	  *主叫次数
	  */
	private Integer originatingCallCount;		
	/**
	  *主叫时长（秒）
	  */
	private Integer originatingTime;		
	/**
	  *被叫次数
	  */
	private Integer terminatingCallCount;		
	/**
	  *被叫时长（秒）
	  */
	private Integer terminatingTime;		
	
	public ReportMnoCcm() {
		super();
	}

	public ReportMnoCcm(String id){
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
	
	public Integer getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(Integer connectCount) {
		this.connectCount = connectCount;
	}
	
	public Integer getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Integer connectTime) {
		this.connectTime = connectTime;
	}
	
	public Integer getOriginatingCallCount() {
		return originatingCallCount;
	}

	public void setOriginatingCallCount(Integer originatingCallCount) {
		this.originatingCallCount = originatingCallCount;
	}
	
	public Integer getOriginatingTime() {
		return originatingTime;
	}

	public void setOriginatingTime(Integer originatingTime) {
		this.originatingTime = originatingTime;
	}
	
	public Integer getTerminatingCallCount() {
		return terminatingCallCount;
	}

	public void setTerminatingCallCount(Integer terminatingCallCount) {
		this.terminatingCallCount = terminatingCallCount;
	}
	
	public Integer getTerminatingTime() {
		return terminatingTime;
	}

	public void setTerminatingTime(Integer terminatingTime) {
		this.terminatingTime = terminatingTime;
	}
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-数据来源Entity
 * @author sunda
 * @version 2017-08-14
 */
public class ReportWebDataSource extends BaseEntity<ReportWebDataSource> {
	
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
	  *数据源-授权获取数据方
	  */
	private String source;		
	/**
	  *数据源类型-授权获取数据方的类型
	  */
	private String sourceType;		
	/**
	  *数据获取时间-存储原始数据时间
	  */
	private String storeTime;		
	/**
	  *数据时间跨度-授权获取数据方多长时间内的数据
	  */
	private String sourceTime;		
	/**
	  *是否实名认证-根据授权获取到的实名认证数据，判别是否认证
	  */
	private boolean passRealName;
	/**
	  *实名信息-授权获取到的实名认证数据
	  */
	private String realNameInfo;		
	/**
	  *数据开始时间
	  */
	private String startTime;		
	/**
	  *实名认证时间
	  */
	private String realCheckTime;		
	/**
	  *是否本人
	  */
	private boolean equalToPetitioner;
	
	public ReportWebDataSource() {
		super();
	}

	public ReportWebDataSource(String id){
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
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public String getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(String storeTime) {
		this.storeTime = storeTime;
	}
	
	public String getSourceTime() {
		return sourceTime;
	}

	public void setSourceTime(String sourceTime) {
		this.sourceTime = sourceTime;
	}
	
	public boolean getPassRealName() {
		return passRealName;
	}

	public void setPassRealName(boolean passRealName) {
		this.passRealName = passRealName;
	}
	
	public String getRealNameInfo() {
		return realNameInfo;
	}

	public void setRealNameInfo(String realNameInfo) {
		this.realNameInfo = realNameInfo;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getRealCheckTime() {
		return realCheckTime;
	}

	public void setRealCheckTime(String realCheckTime) {
		this.realCheckTime = realCheckTime;
	}
	
	public boolean getEqualToPetitioner() {
		return equalToPetitioner;
	}

	public void setEqualToPetitioner(boolean equalToPetitioner) {
		this.equalToPetitioner = equalToPetitioner;
	}
	
}
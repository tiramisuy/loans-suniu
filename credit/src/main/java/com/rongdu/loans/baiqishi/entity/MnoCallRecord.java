/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 白骑士-运营商通话记录Entity
 * @author sunda
 * @version 2017-08-14
 */
public class MnoCallRecord extends BaseEntity<MnoCallRecord> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *运营商数据ID
	  */
	private String mnoDetailId;		
	/**
	  *通话开始时间（主叫或被叫）
	  */
	private String beginTime;		
	/**
	  *通话时长（单位秒）
	  */
	private Integer callDuration;		
	/**
	  *呼叫类型（主叫、被叫）
	  */
	private String callType;		
	/**
	  *业务类型（语音通话）
	  */
	private String businessType;		
	/**
	  *通话对方号码
	  */
	private String otherNum;		
	/**
	  *当前号码通话地点
	  */
	private String homeArea;		
	/**
	  *长途类型（本地、省内长途、国内长途）
	  */
	private String landType;		
	/**
	  *通话产生费用
	  */
	private BigDecimal totalFee;		
	
	public MnoCallRecord() {
		super();
	}

	public MnoCallRecord(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMnoDetailId() {
		return mnoDetailId;
	}

	public void setMnoDetailId(String mnoDetailId) {
		this.mnoDetailId = mnoDetailId;
	}
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public Integer getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(Integer callDuration) {
		this.callDuration = callDuration;
	}
	
	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}
	
	public String getHomeArea() {
		return homeArea;
	}

	public void setHomeArea(String homeArea) {
		this.homeArea = homeArea;
	}
	
	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}
	
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	
}
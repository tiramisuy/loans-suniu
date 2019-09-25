/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 营销客户分配Entity
 * @author liul
 * @version 2018-10-15
 */
public class MarketAllot extends BaseEntity<MarketAllot> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *客户信息ID
	  */
	private String custUserId;		
	/**
	  *被分配的用户id
	  */
	private String userId;		
	/**
	  *分配的用户姓名
	  */
	private String userName;		
	/**
	  *提醒时间
	  */
	private String warnTime;		
	/**
	  *提醒内容
	  */
	private String content;		
	/**
	  *是否提醒
	  */
	private Integer isWarn;		
	/**
	  *拨打情况；1：永久拒绝营销，2：无人接听；3：考虑；4：需要；5：不需要；6,：第三方接听
	  */
	private Integer isPush;		
	/**
	  *拨打次数
	  */
	private Integer callTime;		
	
	private Date allotDate;
	
	public Date getAllotDate() {
		return allotDate;
	}

	public void setAllotDate(Date allotDate) {
		this.allotDate = allotDate;
	}

	public MarketAllot() {
		super();
	}

	public MarketAllot(String id){
		super(id);
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}
	
	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	
	public Integer getCallTime() {
		return callTime;
	}

	public void setCallTime(Integer callTime) {
		this.callTime = callTime;
	}
	
}
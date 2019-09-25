/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 还款提醒Entity
 * @author liuliang
 * @version 2018-05-22
 */
public class LoanMarketManagement extends BaseEntity<LoanMarketManagement> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *申请信息id
	  */
	private String applyId;		
	/**
	  *被分配的用户id
	  */
	private String userId;		
	/**
	  *提醒人员
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
	
	private Integer isPush;
	
	private Date allotDate;
	
	public Date getAllotDate() {
		return allotDate;
	}

	public void setAllotDate(Date allotDate) {
		this.allotDate = allotDate;
	}

	public LoanMarketManagement() {
		super();
	}

	public LoanMarketManagement(String id){
		super(id);
	}

	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
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
	
}
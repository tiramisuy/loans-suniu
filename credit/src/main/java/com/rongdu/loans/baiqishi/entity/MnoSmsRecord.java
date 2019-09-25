/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-运营商短信记录Entity
 * @author sunda
 * @version 2017-08-14
 */
public class MnoSmsRecord extends BaseEntity<MnoSmsRecord> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *原始数据存储时间（时间戳）
	  */
	private String mnoDetailId;		
	/**
	  *短信发送时间
	  */
	private String beginTime;		
	/**
	  *短信业务类型
	  */
	private String businessType;		
	/**
	  *短信接收类型（接收、发送）
	  */
	private String smsType;		
	/**
	  *短信费用
	  */
	private String amount;		
	/**
	  *短信对方号码
	  */
	private String otherNum;		
	
	public MnoSmsRecord() {
		super();
	}

	public MnoSmsRecord(String id){
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
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}
	
}
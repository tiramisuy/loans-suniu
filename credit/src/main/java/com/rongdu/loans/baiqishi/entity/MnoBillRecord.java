/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-运营商账单记录Entity
 * @author sunda
 * @version 2017-08-14
 */
public class MnoBillRecord extends BaseEntity<MnoBillRecord> {
	
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
	private String month;		
	/**
	  *总话费
	  */
	private String allFee;		
	/**
	  *抵扣话费
	  */
	private String deductionFee;		
	/**
	  *缴纳花费
	  */
	private String dueFee;		
	
	public MnoBillRecord() {
		super();
	}

	public MnoBillRecord(String id){
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
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getAllFee() {
		return allFee;
	}

	public void setAllFee(String allFee) {
		this.allFee = allFee;
	}
	
	public String getDeductionFee() {
		return deductionFee;
	}

	public void setDeductionFee(String deductionFee) {
		this.deductionFee = deductionFee;
	}
	
	public String getDueFee() {
		return dueFee;
	}

	public void setDueFee(String dueFee) {
		this.dueFee = dueFee;
	}
	
}
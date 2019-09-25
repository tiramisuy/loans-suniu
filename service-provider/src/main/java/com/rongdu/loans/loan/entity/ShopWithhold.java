/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 购物款代扣Entity
 * @author liuliang
 * @version 2018-05-31
 */
public class ShopWithhold extends BaseEntity<ShopWithhold> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *申请Id
	  */
	private String applyId;		

	/**
	  *扣款用户ID
	  */
	private String custUserId;
	/**
	  *代扣次数
	  */
	private Integer withholdNumber;		
	/**
	  *代扣时间
	  */
	private Date withholdTime;		
	/**
	  *代扣状态
	  */
	private Integer withholdStatus;		
	/**
	  *代扣金额
	  */
	private BigDecimal withholdFee;		
	
	public ShopWithhold() {
		super();
	}

	public ShopWithhold(String id){
		super(id);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public Integer getWithholdNumber() {
		return withholdNumber;
	}

	public void setWithholdNumber(Integer withholdNumber) {
		this.withholdNumber = withholdNumber;
	}
	
	public Date getWithholdTime() {
		return withholdTime;
	}

	public void setWithholdTime(Date withholdTime) {
		this.withholdTime = withholdTime;
	}
	
	public Integer getWithholdStatus() {
		return withholdStatus;
	}

	public void setWithholdStatus(Integer withholdStatus) {
		this.withholdStatus = withholdStatus;
	}
	
	public BigDecimal getWithholdFee() {
		return withholdFee;
	}

	public void setWithholdFee(BigDecimal withholdFee) {
		this.withholdFee = withholdFee;
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}
	
}
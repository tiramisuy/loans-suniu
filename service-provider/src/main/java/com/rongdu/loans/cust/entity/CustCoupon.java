/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 客户卡券表Entity
 * @author raowb
 * @version 2018-08-27
 */
public class CustCoupon extends BaseEntity<CustCoupon> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户id
	  */
	private String userId;		
	/**
	  *申请单号
	  */
	private String applyId;		
	/**
	  *卡券类型7=加急券 8=旅游券 9=购物券
	  */
	private Integer type;		
	/**
	  *卡券名称
	  */
	private String couponName;		
	/**
	  *卡券金额
	  */
	private BigDecimal amount;		
	/**
	  *抵扣率
	  */
	private BigDecimal rate;		
	/**
	  *开始时间
	  */
	private Date startTime;		
	/**
	  *过期时间
	  */
	private Date endTime;		
	/**
	  *来源
	  */
	private String source;		
	/**
	  *是否使用0：未使用 1：已使用
	  */
	private Integer status;		
	
	public CustCoupon() {
		super();
	}

	public CustCoupon(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
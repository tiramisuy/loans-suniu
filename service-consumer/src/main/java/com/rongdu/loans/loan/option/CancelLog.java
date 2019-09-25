/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;

import com.rongdu.common.persistence.BaseEntity;

/**
 * loan_cancel_logEntity
 * @author qf
 * @version 2019-02-26
 */
public class CancelLog extends BaseEntity<CancelLog> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户userId
	  */
	private String userId;		
	/**
	  *姓名
	  */
	private String userName;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *证件号
	  */
	private String idNo;		
	/**
	  *银行卡号
	  */
	private String cardNo;		
	/**
	  *渠道(1:汉金所，2:口袋)
	  */
	private Integer payChannel;		
	
	public CancelLog() {
		super();
	}

	public CancelLog(String id){
		super(id);
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
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}
	
}
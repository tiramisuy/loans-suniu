/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 助贷产品分配表Entity
 * @author liuliang
 * @version 2018-08-29
 */
public class HelpAllot extends BaseEntity<HelpAllot> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *助贷产品id
	  */
	private String borrowId;		
	/**
	  *被分配的用户id
	  */
	private String userId;		
	/**
	  *被分配用户姓名
	  */
	private String userName;		
	
	public HelpAllot() {
		super();
	}

	public HelpAllot(String id){
		super(id);
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
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
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 用户分组表Entity
 * @author liuzhuang
 * @version 2018-04-18
 */
public class CustUserGroup extends BaseEntity<CustUserGroup> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *门店编号
	  */
	private String storeId;		
	/**
	  *组编号
	  */
	private String groupId;		
	/**
	  *用户编号
	  */
	private String userId;		
	
	public CustUserGroup() {
		super();
	}

	public CustUserGroup(String id){
		super(id);
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhima.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 芝麻信用授权Entity
 * @author sunda
 * @version 2017-08-14
 */
public class Authority extends BaseEntity<Authority> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *芝麻账户是否存在
	  */
	private Integer existed;		
	/**
	  *是否授权
	  */
	private Integer authorized;		
	/**
	  *芝麻信用授权ID
	  */
	private String openId;		
	
	public Authority() {
		super();
	}

	public Authority(String id){
		super(id);
	}

	public Integer getExisted() {
		return existed;
	}

	public void setExisted(Integer existed) {
		this.existed = existed;
	}
	
	public Integer getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Integer authorized) {
		this.authorized = authorized;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
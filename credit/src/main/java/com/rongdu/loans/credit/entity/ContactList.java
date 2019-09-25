/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 用户通讯录Entity
 * @author sunda
 * @version 2017-08-14
 */
public class ContactList extends BaseEntity<ContactList> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *联系人姓名
	  */
	private String name;		
	/**
	  *区号
	  */
	private String zone;		
	/**
	  *联系人电话号码（手机、座机）
	  */
	private String telephone;		
	/**
	  *是否为手机号码
	  */
	private Integer isMobile;		
	
	public ContactList() {
		super();
	}

	public ContactList(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Integer getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Integer isMobile) {
		this.isMobile = isMobile;
	}
	
}
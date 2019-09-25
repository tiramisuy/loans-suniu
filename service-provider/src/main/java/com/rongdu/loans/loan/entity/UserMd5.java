/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 用户md5Entity
 * @author Lee
 * @version 2018-07-09
 */
public class UserMd5 extends BaseEntity<UserMd5> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *MD5
	  */
	private String md5;		
	/**
	  *姓名
	  */
	private String realName;		
	/**
	  *证件号
	  */
	private String idNo;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *user_no
	  */
	private String userNo;		
	
	public UserMd5() {
		super();
	}

	public UserMd5(String id){
		super(id);
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
}
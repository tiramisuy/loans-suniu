/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 商品收货地址Entity
 * @author Lee
 * @version 2018-05-09
 */
public class GoodsAddress extends BaseEntity<GoodsAddress> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户id
	  */
	private String userId;
	/**
	  *用户姓名
	  */
	private String userName;		
	/**
	  *订单id
	  */
	private String applyId;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *省
	  */
	private String province;		
	/**
	  *市
	  */
	private String city;		
	/**
	  *区
	  */
	private String district;		
	/**
	  *详细地址
	  */
	private String address;		
	
	public GoodsAddress() {
		super();
	}

	public GoodsAddress(String id){
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
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
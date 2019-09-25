/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.entity;

import java.util.Date;

public class Location{
	
	/**
	  *省/直辖市/自治区/特别行政区
	  */
	private String province;		
	/**
	  *地级市
	  */
	private String city;		
	/**
	  *县/区
	  */
	private String district;		
	/**
	  *街道
	  */
	private String street;			
	/**
	  *详细地址
	  */
	private String address;		
	/**
	  *创建时间
	  */
	private Date createTime;
	
	/**
	 * 纬度 
	 */
	private String latitude;

	/**
	 * 经度
	 */
	private String longitude;
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Location() {
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
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

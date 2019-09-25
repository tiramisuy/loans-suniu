/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 基于IP的地理定位信息Entity
 * @author likang
 * @version 2017-08-15
 */
public class IpLocation extends BaseEntity<IpLocation>{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7968367448442296732L;
	
	/**
	  *IP地址
	  */
	private String ip;		
	
	/**
	 * 用户Id
	 */
	private String userId;		
	/**
	  *经度
	  */
	private BigDecimal longitude;		
	/**
	  *纬度
	  */
	private BigDecimal latitude;		
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
	  *街道号
	  */
	private String streetNumber;		
	/**
	  *详细地址
	  */
	private String address;		
	/**
	  *是否黑名单(0-否，1-是)
	  */
	private Integer status;		
	
	public IpLocation() {
		super();
	}

	public IpLocation(String id){
		super(id);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
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
	
	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}

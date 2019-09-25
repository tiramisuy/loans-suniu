package com.rongdu.loans.cust.option;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class LocationOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6563550076276008493L;
	
	@NotBlank(message="纬度不能为空")
	private String lat;
	@NotBlank(message="经度不能为空")
	private String lng;
	
	private String ip;    //登录ip

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}

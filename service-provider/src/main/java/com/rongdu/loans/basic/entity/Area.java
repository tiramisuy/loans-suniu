/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 地区Entity
 * @author sunda
 * @version 2017-08-23
 */
public class Area extends BaseEntity<Area> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *地区
	  */
	private String name;		
	/**
	  *上级地区ID
	  */
	private String parentId;		
	/**
	  *地区简称
	  */
	private String shortName;		
	/**
	  *地区分级
	  */
	private String levelType;		
	/**
	  *地区代码
	  */
	private String cityCode;		
	/**
	  *邮政编码
	  */
	private String zipCode;		
	/**
	  *省市区名称
	  */
	private String mergeName;		
	/**
	  *经度
	  */
	private String lng;		
	/**
	  *纬度
	  */
	private String lat;		
	/**
	  *拼音
	  */
	private String pinyin;		
	
	public Area() {
		super();
	}

	public Area(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getMergeName() {
		return mergeName;
	}

	public void setMergeName(String mergeName) {
		this.mergeName = mergeName;
	}
	
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-资信报告Entity
 * @author sunda
 * @version 2017-08-22
 */
public class Report extends BaseEntity<Report> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *报告时间
	  */
	private String reportTime;		
	/**
	  *姓名
	  */
	private String name;		
	/**
	  *身份证
	  */
	private String certNo;		
	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *手机号码归属地
	  */
	private String mobileBelongTo;		
	/**
	  *电信运营商
	  */
	private String mobileMnoType;		
	/**
	  *性别
	  */
	private String gender;		
	/**
	  *星座
	  */
	private String constellation;		
	/**
	  *年龄
	  */
	private Integer age;		
	/**
	  *出生地
	  */
	private String birthAddress;		
	/**
	  *备注
	  */
	private String bak;		
	
	public Report() {
		super();
	}

	public Report(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getMobileBelongTo() {
		return mobileBelongTo;
	}

	public void setMobileBelongTo(String mobileBelongTo) {
		this.mobileBelongTo = mobileBelongTo;
	}
	
	public String getMobileMnoType() {
		return mobileMnoType;
	}

	public void setMobileMnoType(String mobileMnoType) {
		this.mobileMnoType = mobileMnoType;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}
	
	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}
	
}
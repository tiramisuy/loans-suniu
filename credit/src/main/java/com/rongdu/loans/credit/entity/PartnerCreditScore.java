/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 第三方(合作机构）信用分Entity
 * @author sunda
 * @version 2017-08-16
 */
public class PartnerCreditScore extends BaseEntity<PartnerCreditScore> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *身份证号
	  */
	private String idNo;		
	/**
	  *姓名
	  */
	private String name;		
	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *信用评分
	  */
	private Integer score;		
	/**
	  *刷新时间
	  */
	private String refreshTime;		
	/**
	  *第三方(合作机构）代码
	  */
	private String parterId;		
	/**
	  *第三方(合作机构）名称
	  */
	private String parterName;		
	
	public PartnerCreditScore() {
		super();
	}

	public PartnerCreditScore(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	public String getParterId() {
		return parterId;
	}

	public void setParterId(String parterId) {
		this.parterId = parterId;
	}
	
	public String getParterName() {
		return parterName;
	}

	public void setParterName(String parterName) {
		this.parterName = parterName;
	}
	
}
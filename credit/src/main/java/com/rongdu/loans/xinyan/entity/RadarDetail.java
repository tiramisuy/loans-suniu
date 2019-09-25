/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.xinyan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 新颜全景雷达明细Entity
 * @author liuzhuang
 * @version 2018-11-19
 */
public class RadarDetail extends BaseEntity<RadarDetail> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *申请编号
	  */
	private String applyId;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *用户姓名
	  */
	private String userName;		
	/**
	  *聚宝袋分
	  */
	private Integer score;		
	
	public RadarDetail() {
		super();
	}

	public RadarDetail(String id){
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
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-运营商数据Entity
 * @author sunda
 * @version 2017-08-14
 */
public class MnoDetail extends BaseEntity<MnoDetail> {
	
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
	  *原始数据存储时间（时间戳）
	  */
	private String storeTime;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *是否实名制
	  */
	private String isRealCheck;		
	/**
	  *实名认证身份证
	  */
	private String boundCertNo;		
	/**
	  *绑定姓名
	  */
	private String boundName;		
	/**
	  *开通时间
	  */
	private String openTime;		
	/**
	  *运营商类型
	  */
	private String monType;		
	/**
	  *归属地
	  */
	private String belongTo;		
	/**
	  *当前号码关联手机号
	  */
	private String relationMobiles;		
	/**
	  *手机当前手机状态
	  */
	private String status;		
	
	public MnoDetail() {
		super();
	}

	public MnoDetail(String id){
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
	
	public String getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(String storeTime) {
		this.storeTime = storeTime;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getIsRealCheck() {
		return isRealCheck;
	}

	public void setIsRealCheck(String isRealCheck) {
		this.isRealCheck = isRealCheck;
	}
	
	public String getBoundCertNo() {
		return boundCertNo;
	}

	public void setBoundCertNo(String boundCertNo) {
		this.boundCertNo = boundCertNo;
	}
	
	public String getBoundName() {
		return boundName;
	}

	public void setBoundName(String boundName) {
		this.boundName = boundName;
	}
	
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	
	public String getMonType() {
		return monType;
	}

	public void setMonType(String monType) {
		this.monType = monType;
	}
	
	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	
	public String getRelationMobiles() {
		return relationMobiles;
	}

	public void setRelationMobiles(String relationMobiles) {
		this.relationMobiles = relationMobiles;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
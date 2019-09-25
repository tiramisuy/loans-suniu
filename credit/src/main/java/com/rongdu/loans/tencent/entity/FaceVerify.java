/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 腾讯人脸验证Entity
 * @author sunda
 * @version 2017-08-14
 */
public class FaceVerify extends BaseEntity<FaceVerify> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *证件姓名
	  */
	private String name;		
	/**
	  *身份证号
	  */
	private String idNo;		
	/**
	  *人像面照片
	  */
	private String photo;		
	/**
	  *国徽面照片
	  */
	private String video;		
	/**
	  *腾讯业务流水号
	  */
	private String bizSeqNo;		
	
	public FaceVerify() {
		super();
	}

	public FaceVerify(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}
	
}
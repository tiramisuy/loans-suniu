/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 腾讯身份证OCR识别结果Entity
 * @author sunda
 * @version 2017-08-14
 */
public class OcrResult extends BaseEntity<OcrResult> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *&ldquo;0&rdquo;说明人像面识别成功
	  */
	private String frontCode;		
	/**
	  *&ldquo;0&rdquo;说明国徽面识别成功
	  */
	private String backCode;		
	/**
	  *frontCode 为 0 返回:证件姓名
	  */
	private String name;		
	/**
	  *frontCode 为 0 返回:性别
	  */
	private String sex;		
	/**
	  *frontCode 为 0 返回:民族
	  */
	private String nation;		
	/**
	  *frontCode 为 0 返回:出生日期
	  */
	private String birth;		
	/**
	  *frontCode 为 0 返回:地址
	  */
	private String address;		
	/**
	  *frontCode 为 0 返回:身份证号
	  */
	private String idcard;		
	/**
	  *backCode为0返回:证件的有效期，起始日期
	  */
	private String validDateStart;		
	/**
	  *backCode为0返回：证件的有效期，失效日期
	  */
	private String validDateEnd;		
	/**
	  *backCode 为 0 返回:发证机关
	  */
	private String authority;		
	/**
	  *人像面照片
	  */
	private String frontPhoto;		
	/**
	  *国徽面照片
	  */
	private String backPhoto;		
	/**
	  *OCR操作时间
	  */
	private String operateTime;		
	/**
	  *腾讯业务流水号
	  */
	private String bizSeqNo;		
	
	public OcrResult() {
		super();
	}

	public OcrResult(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFrontCode() {
		return frontCode;
	}

	public void setFrontCode(String frontCode) {
		this.frontCode = frontCode;
	}
	
	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	public String getValidDateStart() {
		return validDateStart;
	}

	public void setValidDateStart(String validDateStart) {
		this.validDateStart = validDateStart;
	}
	
	public String getValidDateEnd() {
		return validDateEnd;
	}

	public void setValidDateEnd(String validDateEnd) {
		this.validDateEnd = validDateEnd;
	}
	
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String getFrontPhoto() {
		return frontPhoto;
	}

	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}
	
	public String getBackPhoto() {
		return backPhoto;
	}

	public void setBackPhoto(String backPhoto) {
		this.backPhoto = backPhoto;
	}
	
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}
	
}
package com.rongdu.loans.cust.vo;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.loans.enums.RelationshipEnum;

/**
 * 联系人表映射数据实体 Created by likang on 2017/6/27.
 */
public class CustContactVO extends BaseEntity<CustContactVO> {

	private String userId; // 客户ID
	/**
	 * 与本人关系: 1父母，2配偶，3朋友，4同事
	 */
	private Integer relationship;
	/**
	 * 与本人关系
	 */
	private String relationshipStr;
	/**
	 * 联系人姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 来源 （1：进件；2：催收）
	 */
	private String source;

	/**
	 * 是否匹配呼入
	 */
	private int isTerminatingCall = 0;
	/**
	 * 是否匹配呼出
	 */
	private int isOriginatingCall = 0;
	/**
	 * 是否匹配设备通讯录
	 */
	private int isDeviceContact = 0;

	public CustContactVO() {
		this.relationship = 1;
		this.relationshipStr = RelationshipEnum.getDesc(relationship);
		this.name = "";
		this.mobile = "";
	}

	public Integer getRelationship() {
		return relationship;
	}

	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
		this.relationshipStr = RelationshipEnum.getDesc(relationship);
	}

	public String getRelationshipStr() {
		return relationshipStr;
	}

	public void setRelationshipStr(String relationshipStr) {
		this.relationshipStr = relationshipStr;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getIsTerminatingCall() {
		return isTerminatingCall;
	}

	public void setIsTerminatingCall(int isTerminatingCall) {
		this.isTerminatingCall = isTerminatingCall;
	}

	public int getIsOriginatingCall() {
		return isOriginatingCall;
	}

	public void setIsOriginatingCall(int isOriginatingCall) {
		this.isOriginatingCall = isOriginatingCall;
	}

	public int getIsDeviceContact() {
		return isDeviceContact;
	}

	public void setIsDeviceContact(int isDeviceContact) {
		this.isDeviceContact = isDeviceContact;
	}

}

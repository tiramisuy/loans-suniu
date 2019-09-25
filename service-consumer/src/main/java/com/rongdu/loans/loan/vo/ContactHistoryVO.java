/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;


import java.io.Serializable;
import java.util.Date;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.RelationshipEnum;

/**
 * 历次贷款申请时的紧急联系人信息Entity
 * @author zhangxiaolong
 * @version 2017-08-01
 */
public class ContactHistoryVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4819039869612068352L;
	/**
	 *原客户姓名
	 */
	private String sourceUserName;
	/**
	 *原客户关系
	 */
	private Integer sourceRelationship;
	/**
	 *原客户手机号
	 */
	private String sourceMobile;
	/**
	 *客户ID
	 */
	private String userId;
	/**
	  *贷款申请编号
	  */
	private String applyId;
	/**
	 * 进件时间
	 */
	private Date createTime;
	/**
	  *与本人关系(1父母，2配偶，3朋友，4同事)
	  */
	private Integer relationship;
	private String relationshipStr;
	/**
	  *联系人姓名
	  */
	private String name;
	/**
	  *手机号码
	  */
	private String mobile;

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
	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
		this.relationshipStr = RelationshipEnum.getDesc(relationship);
	}

	public Integer getRelationship() {
		return relationship;
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

	public String getSourceUserName() {
		return sourceUserName;
	}

	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}

	public String getSourceRelationship() {
		String desc = RelationshipEnum.getDesc(sourceRelationship);
		return StringUtils.isNotBlank(desc)?desc:"本人";
	}

	public void setSourceRelationship(Integer sourceRelationship) {
		this.sourceRelationship = sourceRelationship;
	}

	public String getSourceMobile() {
		return sourceMobile;
	}

	public void setSourceMobile(String sourceMobile) {
		this.sourceMobile = sourceMobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 历次贷款申请时的紧急联系人信息Entity
 * @author zhangxiaolong
 * @version 2017-08-01
 */
public class ContactHistory extends BaseEntity<ContactHistory> {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 默认来源
	 */
	public static final String SOURCE_DEF = "1";
	/**
	 * 催收来源
	 */
	public static final String SOURCE_COL = "2";
	
	/**
	 * 催收来源
	 */
	public static final String SOURCE_COL_DESC = "新增";
	
	/**
	  *客户ID
	  */
	private String userId;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *与本人关系(1父母，2配偶，3朋友，4同事)
	  */
	private Integer relationship;		
	/**
	  *联系人姓名
	  */
	private String name;		
	/**
	  *手机号码
	  */
	private String mobile;	
	
	/**
	  * 来源  （1：进件；2：催收）
	  */
	private String source;
	
	public ContactHistory() {
		super();
	}

	public ContactHistory(String id){
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
	
	public Integer getRelationship() {
		return relationship;
	}

	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
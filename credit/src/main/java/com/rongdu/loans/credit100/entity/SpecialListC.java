/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit100.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 百融-特殊名单核查Entity
 * @author sunda
 * @version 2017-08-14
 */
public class SpecialListC extends BaseEntity<SpecialListC> {
	
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
	  *特殊名单代码
	  */
	private String key;		
	/**
	  *特殊名单说明
	  */
	private String keyDesc;		
	/**
	  *取值
	  */
	private String value;		
	/**
	  *取值说明
	  */
	private String valueDesc;		
	
	public SpecialListC() {
		super();
	}

	public SpecialListC(String id){
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
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKeyDesc() {
		return keyDesc;
	}

	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}
	
}
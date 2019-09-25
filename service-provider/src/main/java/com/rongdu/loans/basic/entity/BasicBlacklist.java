/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 基础黑名单（手机号、ip等）Entity
 * @author likang
 * @version 2017-10-10
 */
public class BasicBlacklist extends BaseEntity<BasicBlacklist>{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2492838424612415174L;
	
	/**
	  *黑名单类型（1：手机号； 2：ip）
	  */
	private Integer blType;		
	/**
	  *黑名单内容（手机号或ip）
	  */
	private String blValue;		
	/**
	  *添加日期(YYYY-MM-DD)
	  */
	private String blDate;		
	/**
	  *渠道（JUQIANBAO-聚宝钱包；LVKA-旅咖）
	  */
	private String channel;

	public BasicBlacklist() {
		super();
	}

	public BasicBlacklist(String id){
		super(id);
	}

	public Integer getBlType() {
		return blType;
	}

	public void setBlType(Integer blType) {
		this.blType = blType;
	}
	
	public String getBlValue() {
		return blValue;
	}

	public void setBlValue(String blValue) {
		this.blValue = blValue;
	}
	
	public String getBlDate() {
		return blDate;
	}

	public void setBlDate(String blDate) {
		this.blDate = blDate;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}

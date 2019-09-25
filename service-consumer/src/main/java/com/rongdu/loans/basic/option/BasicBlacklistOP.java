package com.rongdu.loans.basic.option;

import java.io.Serializable;

public class BasicBlacklistOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	  *黑名单类型（1：手机号； 2：ip）
	  */
	private Integer blType;	
	
	/**
	  *黑名单内容（手机号或ip）
	  */
	private String blValue;		
	
	/**
	  *渠道（JUQIANBAO-聚宝钱包；LVKA-旅咖）
	  */
	private String channel;

	public Integer getBlType() {
		return blType;
	}

	public String getBlValue() {
		return blValue;
	}

	public String getChannel() {
		return channel;
	}

	public void setBlType(Integer blType) {
		this.blType = blType;
	}

	public void setBlValue(String blValue) {
		this.blValue = blValue;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}

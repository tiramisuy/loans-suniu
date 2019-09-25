/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 风控策略Entity
 * @author sunda
 * @version 2017-08-27
 */
public class Strategy extends BaseEntity<Strategy> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *策略(集)名称
	  */
	private String name;		
	/**
	  *层级(1-策略集，2-策略)
	  */
	private Integer level;		
	/**
	  *风险类型代码（risk_category的level为1时的风险）
	  */
	private String riskTypeCode;		
	/**
	  *策略的匹配模式：FirstMode-首次匹配，WeightMode-权重匹配，WorstMode-最坏匹配
	  */
	private String matchMode;		
	/**
	  *策略集ID
	  */
	private String pid;		
	/**
	  *风险事件ID
	  */
	private String eventId;		
	
	public Strategy() {
		super();
	}

	public Strategy(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getRiskTypeCode() {
		return riskTypeCode;
	}

	public void setRiskTypeCode(String riskTypeCode) {
		this.riskTypeCode = riskTypeCode;
	}
	
	public String getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(String matchMode) {
		this.matchMode = matchMode;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
}
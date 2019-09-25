/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 贷超导流统计Entity
 * @author raowb
 * @version 2018-08-29
 */
public class LoanTrafficStatistics extends BaseEntity<LoanTrafficStatistics> {
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	  *贷超产品id
	  */
	private String trafficId;		
	/**
	  *贷超产品名称
	  */
	private String trafficName;		
	/**
	  *浏览量pv
	  */
	private Integer views;		
	/**
	  *点击数uv
	  */
	private Integer hits;		
	/**
	  *统计时间年月日YYYYMMDD
	  */
	private Integer statsDate;		
	/**
	  *状态(0-有效，1-无效)
	  */
	private String status;		
	private Integer beginStatsDate;		// 开始 统计时间年月日YYYYMMDD
	private Integer endStatsDate;		// 结束 统计时间年月日YYYYMMDD
	
	public LoanTrafficStatistics() {
		super();
	}

	public LoanTrafficStatistics(String id){
		super(id);
	}

	public String getTrafficId() {
		return trafficId;
	}

	public void setTrafficId(String trafficId) {
		this.trafficId = trafficId;
	}
	
	public String getTrafficName() {
		return trafficName;
	}

	public void setTrafficName(String trafficName) {
		this.trafficName = trafficName;
	}
	
	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
	
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	public Integer getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(Integer statsDate) {
		this.statsDate = statsDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getBeginStatsDate() {
		return beginStatsDate;
	}

	public void setBeginStatsDate(Integer beginStatsDate) {
		this.beginStatsDate = beginStatsDate;
	}

	public Integer getEndStatsDate() {
		return endStatsDate;
	}

	public void setEndStatsDate(Integer endStatsDate) {
		this.endStatsDate = endStatsDate;
	}

	
	
		
}
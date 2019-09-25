package com.rongdu.loans.loan.option;

import java.io.Serializable;

public class LoanTrafficStatisticsOP implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	*贷超产品id
	*/
	private String trafficId;

	public String getTrafficId() {
		return trafficId;
	}

	public void setTrafficId(String trafficId) {
		this.trafficId = trafficId;
	}
	
	private Integer beginStatsDate;		// 开始 统计时间年月日YYYYMMDD
	private Integer endStatsDate;		// 结束 统计时间年月日YYYYMMDD

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

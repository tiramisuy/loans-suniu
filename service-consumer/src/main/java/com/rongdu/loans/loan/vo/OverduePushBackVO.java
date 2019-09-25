package com.rongdu.loans.loan.vo;

import java.io.Serializable;

public class OverduePushBackVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6624936806816126031L;

	
	public String userName;	//催收人
	
	public Integer allSettlement;	//全部已结清
	
	public Integer todaySettlement; //今日已结清
	
	public Integer allOutstanding; //全部未结清
	
	public Integer todayOutstanding;	//今日未结清

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAllSettlement() {
		return allSettlement;
	}

	public void setAllSettlement(Integer allSettlement) {
		this.allSettlement = allSettlement;
	}

	public Integer getTodaySettlement() {
		return todaySettlement;
	}

	public void setTodaySettlement(Integer todaySettlement) {
		this.todaySettlement = todaySettlement;
	}

	public Integer getAllOutstanding() {
		return allOutstanding;
	}

	public void setAllOutstanding(Integer allOutstanding) {
		this.allOutstanding = allOutstanding;
	}

	public Integer getTodayOutstanding() {
		return todayOutstanding;
	}

	public void setTodayOutstanding(Integer todayOutstanding) {
		this.todayOutstanding = todayOutstanding;
	}
	
	

}

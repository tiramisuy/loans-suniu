package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

public class JbqbBlackResultDetailVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String max_overdue_amt;
	private String max_overdue_days;
	private String latest_overdue_time;
	private String currently_overdue;
	private String currently_performance;
	private String acc_exc;
	private String acc_sleep;
	private String tag_jubaodai;

	public String getMax_overdue_amt() {
		return max_overdue_amt;
	}

	public void setMax_overdue_amt(String max_overdue_amt) {
		this.max_overdue_amt = max_overdue_amt;
	}

	public String getMax_overdue_days() {
		return max_overdue_days;
	}

	public void setMax_overdue_days(String max_overdue_days) {
		this.max_overdue_days = max_overdue_days;
	}

	public String getLatest_overdue_time() {
		return latest_overdue_time;
	}

	public void setLatest_overdue_time(String latest_overdue_time) {
		this.latest_overdue_time = latest_overdue_time;
	}

	public String getCurrently_overdue() {
		return currently_overdue;
	}

	public void setCurrently_overdue(String currently_overdue) {
		this.currently_overdue = currently_overdue;
	}

	public String getCurrently_performance() {
		return currently_performance;
	}

	public void setCurrently_performance(String currently_performance) {
		this.currently_performance = currently_performance;
	}

	public String getAcc_exc() {
		return acc_exc;
	}

	public void setAcc_exc(String acc_exc) {
		this.acc_exc = acc_exc;
	}

	public String getAcc_sleep() {
		return acc_sleep;
	}

	public void setAcc_sleep(String acc_sleep) {
		this.acc_sleep = acc_sleep;
	}

	public String getTag_jubaodai() {
		return tag_jubaodai;
	}

	public void setTag_jubaodai(String tag_jubaodai) {
		this.tag_jubaodai = tag_jubaodai;
	}

}

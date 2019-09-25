package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 通话记录
 * @author sunda
 * @version 2017-07-10
 */
public class CallRecord implements Serializable {

	private static final long serialVersionUID = -4772970826740978137L;
	/**
	 * 通话时间
	 */
	public String callTime;
	/**
	 * 通话时长(单位:秒)
	 */
	public String duration;
	/**
	 * 对方姓名
	 */
	public String name;
	/**
	 * 呼入/呼出/挂断/未接
	 */
	public String type;

	public CallRecord() {
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}

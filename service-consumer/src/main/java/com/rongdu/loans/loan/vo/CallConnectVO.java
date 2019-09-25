package com.rongdu.loans.loan.vo;


import java.io.Serializable;

/**
 * 
* @Description: 呼叫记录 
* @author: RaoWenbiao
* @date 2019年2月25日
 */
public class CallConnectVO implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1;
	/**
	 * 匹配通讯录联系人
	 */
	 private String contactName;
	
	/**
	 * 通话号码
	 */
	private String phone;
	
	/**
	 * 归属地
	 */
	 private String phoneLocation;
	
	/**
	 * 最近一周联系次数
	 */
	private Integer contact1w;
	
	/**
	 * 最近一月联系次数
	 */
	private Integer contact1m;
	
	/**
	 * 最近三月联系次数
	 */
	private Integer contact3m;
	
	/**
	 * 通话次数
	 */
	private Integer talkCnt;
	
	/**
	 * 通话时间
	 */
	private Integer talkSeconds;
	
	/**
	 * 被叫次数
	 */
	private Integer calledCnt;
	
	/**
	 * 被叫时间
	 */
	private Integer calledSeconds;
	
	/**
	 * 呼出次数
	 */
	private Integer callCnt;
	
	/**
	 * 呼出时间
	 */
	private Integer callSeconds;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneLocation() {
		return phoneLocation;
	}

	public void setPhoneLocation(String phoneLocation) {
		this.phoneLocation = phoneLocation;
	}

	public Integer getContact1w() {
		return contact1w;
	}

	public void setContact1w(Integer contact1w) {
		this.contact1w = contact1w;
	}

	public Integer getContact1m() {
		return contact1m;
	}

	public void setContact1m(Integer contact1m) {
		this.contact1m = contact1m;
	}

	public Integer getContact3m() {
		return contact3m;
	}

	public void setContact3m(Integer contact3m) {
		this.contact3m = contact3m;
	}

	public Integer getTalkCnt() {
		return talkCnt;
	}

	public void setTalkCnt(Integer talkCnt) {
		this.talkCnt = talkCnt;
	}

	public Integer getTalkSeconds() {
		return talkSeconds;
	}

	public void setTalkSeconds(Integer talkSeconds) {
		this.talkSeconds = talkSeconds;
	}

	public Integer getCalledCnt() {
		return calledCnt;
	}

	public void setCalledCnt(Integer calledCnt) {
		this.calledCnt = calledCnt;
	}

	public Integer getCalledSeconds() {
		return calledSeconds;
	}

	public void setCalledSeconds(Integer calledSeconds) {
		this.calledSeconds = calledSeconds;
	}

	public Integer getCallCnt() {
		return callCnt;
	}

	public void setCallCnt(Integer callCnt) {
		this.callCnt = callCnt;
	}

	public Integer getCallSeconds() {
		return callSeconds;
	}

	public void setCallSeconds(Integer callSeconds) {
		this.callSeconds = callSeconds;
	}
	
	
}

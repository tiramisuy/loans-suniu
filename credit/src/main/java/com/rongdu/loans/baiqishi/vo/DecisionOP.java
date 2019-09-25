package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 白骑士-反欺诈云风控决策-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class DecisionOP implements Serializable {

	private static final long serialVersionUID = 1687027729736356630L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 证件号码
	 */
	private String certNo;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 事件发生时间
	 * 格式  yyyy-MM-dd HH:mm:ss
	 */
	private String occurTime;
	/**
	 * 用户联系人姓名
	 */
	private String contactsName;
	/**
	 * 用户联系人 手机号
	 */
	private String contactsMobile;
	/**
	 * 用户第二联系人姓名
	 */
	private String contactsNameSec;
	/**
	 * 用户第二联系人手机号
	 */
	private String contactsMobileSec;
	/**
	 * 用户App端的IP地址
	 */
	private String ip;
	/**
	 * 应用平台类型 ， h5/web/ios/android
	 */
	private String platform;
	/**
	 * 白骑士设备ID
	 */
	private String tokenKey;
	/**
	 * 事件类型
	 */
	private String eventType;
	/**
	 * 是否复贷
	 */
	private String loanSuccCount;
	/**
	 * 渠道Id
	 */
	private String channelId;

	public DecisionOP() {
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

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public String getContactsNameSec() {
		return contactsNameSec;
	}

	public void setContactsNameSec(String contactsNameSec) {
		this.contactsNameSec = contactsNameSec;
	}

	public String getContactsMobileSec() {
		return contactsMobileSec;
	}

	public void setContactsMobileSec(String contactsMobileSec) {
		this.contactsMobileSec = contactsMobileSec;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setLoanSuccCount(String loanSuccCount) {
		this.loanSuccCount = loanSuccCount;
	}

	public String getLoanSuccCount() {
		return loanSuccCount;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}

/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.entity;

import java.util.List;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 白骑士-交叉信息验证Entity
 * 
 * @author liuzhuang
 * @version 2017-11-20
 */
public class ReportCrossValidation extends BaseEntity<ReportCrossValidation> {

	private static final long serialVersionUID = 1L;
	/**
	 * 通话主要活动区域
	 */
	private ReportCrossValidationDetail callActiveArea;
	/**
	 * 朋友圈活动区域
	 */
	private ReportCrossValidationDetail contactsActiveArea;
	/**
	 * 朋友圈大小
	 */
	private ReportCrossValidationDetail contactsSize;
	/**
	 * 朋友圈活跃度
	 */
	private ReportCrossValidationDetail contactsActiveDegree;
	/**
	 * 入网时间
	 */
	private ReportCrossValidationDetail openTime;
	/**
	 * 号码使用时长
	 */
	private ReportCrossValidationDetail numberUsedLong;
	/**
	 * 未使用通话与短信的天数
	 */
	private ReportCrossValidationDetail notCallAndSmsDayCount;

	/**
	 * 贷款类号码使用情况
	 */
	private ReportCrossValidationDetail p2pConnectInfo;
	/**
	 * 互通电话号码数
	 */
	private ReportCrossValidationDetail exchangeCallMobileCount;
	/**
	 * 夜间通话次数
	 */
	private ReportCrossValidationDetail nightCallCount;

	/**
	 * 特殊通话联系情况
	 */
	private List<ReportCrossValidationDetail> exceptionalConnectInfoList;

	/**
	 * 拨入电话号码数
	 */
	private ReportCrossValidationDetail terminatingCallCount;
	/**
	 * 拨出电话号码数
	 */
	private ReportCrossValidationDetail originatingMobileCount;
	/**
	 * 通话时长在1分钟以内的通话记录个数
	 */
	private ReportCrossValidationDetail callDurationLess1minSize;
	/**
	 * 通话时长在1~5分钟以内的通话次数
	 */
	private ReportCrossValidationDetail callDuration1to5minSize;
	/**
	 * 通话时长在5~10分钟以内的通话次数
	 */
	private ReportCrossValidationDetail callDuration5to10minSize;
	/**
	 * 通话时长在10分钟以上的通话次数
	 */
	private ReportCrossValidationDetail callDurationBigger10minSize;
	/**
	 * 单次拨入通话最长时长
	 */
	private ReportCrossValidationDetail terminatingCallDurationMax;
	/**
	 * 单次拨出通话最长时长
	 */
	private ReportCrossValidationDetail originatingCallDurationMax;

	public ReportCrossValidationDetail getOpenTime() {
		return openTime;
	}

	public void setOpenTime(ReportCrossValidationDetail openTime) {
		this.openTime = openTime;
	}

	public ReportCrossValidationDetail getNumberUsedLong() {
		return numberUsedLong;
	}

	public void setNumberUsedLong(ReportCrossValidationDetail numberUsedLong) {
		this.numberUsedLong = numberUsedLong;
	}

	public ReportCrossValidationDetail getNotCallAndSmsDayCount() {
		return notCallAndSmsDayCount;
	}

	public void setNotCallAndSmsDayCount(ReportCrossValidationDetail notCallAndSmsDayCount) {
		this.notCallAndSmsDayCount = notCallAndSmsDayCount;
	}

	public ReportCrossValidationDetail getP2pConnectInfo() {
		return p2pConnectInfo;
	}

	public void setP2pConnectInfo(ReportCrossValidationDetail p2pConnectInfo) {
		this.p2pConnectInfo = p2pConnectInfo;
	}

	public ReportCrossValidationDetail getOriginatingMobileCount() {
		return originatingMobileCount;
	}

	public void setOriginatingMobileCount(ReportCrossValidationDetail originatingMobileCount) {
		this.originatingMobileCount = originatingMobileCount;
	}

	public ReportCrossValidationDetail getExchangeCallMobileCount() {
		return exchangeCallMobileCount;
	}

	public void setExchangeCallMobileCount(ReportCrossValidationDetail exchangeCallMobileCount) {
		this.exchangeCallMobileCount = exchangeCallMobileCount;
	}

	public ReportCrossValidationDetail getCallActiveArea() {
		return callActiveArea;
	}

	public void setCallActiveArea(ReportCrossValidationDetail callActiveArea) {
		this.callActiveArea = callActiveArea;
	}

	public ReportCrossValidationDetail getContactsActiveArea() {
		return contactsActiveArea;
	}

	public void setContactsActiveArea(ReportCrossValidationDetail contactsActiveArea) {
		this.contactsActiveArea = contactsActiveArea;
	}

	public ReportCrossValidationDetail getContactsSize() {
		return contactsSize;
	}

	public void setContactsSize(ReportCrossValidationDetail contactsSize) {
		this.contactsSize = contactsSize;
	}

	public ReportCrossValidationDetail getContactsActiveDegree() {
		return contactsActiveDegree;
	}

	public void setContactsActiveDegree(ReportCrossValidationDetail contactsActiveDegree) {
		this.contactsActiveDegree = contactsActiveDegree;
	}

	public ReportCrossValidationDetail getNightCallCount() {
		return nightCallCount;
	}

	public void setNightCallCount(ReportCrossValidationDetail nightCallCount) {
		this.nightCallCount = nightCallCount;
	}

	public List<ReportCrossValidationDetail> getExceptionalConnectInfoList() {
		return exceptionalConnectInfoList;
	}

	public void setExceptionalConnectInfoList(List<ReportCrossValidationDetail> exceptionalConnectInfoList) {
		this.exceptionalConnectInfoList = exceptionalConnectInfoList;
	}

	public ReportCrossValidationDetail getTerminatingCallCount() {
		return terminatingCallCount;
	}

	public void setTerminatingCallCount(ReportCrossValidationDetail terminatingCallCount) {
		this.terminatingCallCount = terminatingCallCount;
	}

	public ReportCrossValidationDetail getCallDurationLess1minSize() {
		return callDurationLess1minSize;
	}

	public void setCallDurationLess1minSize(ReportCrossValidationDetail callDurationLess1minSize) {
		this.callDurationLess1minSize = callDurationLess1minSize;
	}

	public ReportCrossValidationDetail getCallDuration1to5minSize() {
		return callDuration1to5minSize;
	}

	public void setCallDuration1to5minSize(ReportCrossValidationDetail callDuration1to5minSize) {
		this.callDuration1to5minSize = callDuration1to5minSize;
	}

	public ReportCrossValidationDetail getCallDuration5to10minSize() {
		return callDuration5to10minSize;
	}

	public void setCallDuration5to10minSize(ReportCrossValidationDetail callDuration5to10minSize) {
		this.callDuration5to10minSize = callDuration5to10minSize;
	}

	public ReportCrossValidationDetail getCallDurationBigger10minSize() {
		return callDurationBigger10minSize;
	}

	public void setCallDurationBigger10minSize(ReportCrossValidationDetail callDurationBigger10minSize) {
		this.callDurationBigger10minSize = callDurationBigger10minSize;
	}

	public ReportCrossValidationDetail getTerminatingCallDurationMax() {
		return terminatingCallDurationMax;
	}

	public void setTerminatingCallDurationMax(ReportCrossValidationDetail terminatingCallDurationMax) {
		this.terminatingCallDurationMax = terminatingCallDurationMax;
	}

	public ReportCrossValidationDetail getOriginatingCallDurationMax() {
		return originatingCallDurationMax;
	}

	public void setOriginatingCallDurationMax(ReportCrossValidationDetail originatingCallDurationMax) {
		this.originatingCallDurationMax = originatingCallDurationMax;
	}

}
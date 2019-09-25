package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 催收指标明细
 * 
 * @author liuzhuang
 * @version 2017-07-10
 */
public class MnoCuiShouInfoDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8101822316146818137L;
	// 时间段类型(近1周,近2周,近3周,近1个月,30天-60天,60天-90天,近6个月)
	private String periodType;
	// 通话号码数
	private Integer connectMobileSize;
	// 通话次数
	private Integer connectCount;
	// 主叫次数
	private Integer callOutCount;
	// 被叫次数
	private Integer callInCount;
	// 通话时长（秒）
	private Integer callTime;
	// 平均通话时长（秒）
	private Double callAvgTime;
	// 主叫时长（秒）
	private Integer callOutTime;
	// 被叫时长（秒）
	private Integer callInTime;
	// 通话时长小于15s的次数
	private Integer callTimeBelow15;
	// 通话时长在15s-30s之间的次数
	private Integer callTimeBetween15and30;
	// 通话时长大于60s的次数
	private Integer callTimeAbove60;
	// 第一次通话时间所在时间区域（无，近3天，近4-5天，近6-7天，近8-15天，近16-30天，近31-60天，近61-90天，近91-120天，近121-150天，近151-180天，180天前）
	private String beginTime;
	// 最近一次通话时间所在时间区域（无，近3天，近4-5天，近6-7天，近8-15天，近16-30天，近31-60天，近61-90天，近91-120天，近121-150天，近151-180天，180天前）
	private String endTime;
	
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public Integer getConnectMobileSize() {
		return connectMobileSize;
	}
	public void setConnectMobileSize(Integer connectMobileSize) {
		this.connectMobileSize = connectMobileSize;
	}
	public Integer getConnectCount() {
		return connectCount;
	}
	public void setConnectCount(Integer connectCount) {
		this.connectCount = connectCount;
	}
	public Integer getCallOutCount() {
		return callOutCount;
	}
	public void setCallOutCount(Integer callOutCount) {
		this.callOutCount = callOutCount;
	}
	public Integer getCallInCount() {
		return callInCount;
	}
	public void setCallInCount(Integer callInCount) {
		this.callInCount = callInCount;
	}
	public Integer getCallTime() {
		return callTime;
	}
	public void setCallTime(Integer callTime) {
		this.callTime = callTime;
	}
	public Double getCallAvgTime() {
		return callAvgTime;
	}
	public void setCallAvgTime(Double callAvgTime) {
		this.callAvgTime = callAvgTime;
	}
	public Integer getCallOutTime() {
		return callOutTime;
	}
	public void setCallOutTime(Integer callOutTime) {
		this.callOutTime = callOutTime;
	}
	public Integer getCallInTime() {
		return callInTime;
	}
	public void setCallInTime(Integer callInTime) {
		this.callInTime = callInTime;
	}
	public Integer getCallTimeBelow15() {
		return callTimeBelow15;
	}
	public void setCallTimeBelow15(Integer callTimeBelow15) {
		this.callTimeBelow15 = callTimeBelow15;
	}
	public Integer getCallTimeBetween15and30() {
		return callTimeBetween15and30;
	}
	public void setCallTimeBetween15and30(Integer callTimeBetween15and30) {
		this.callTimeBetween15and30 = callTimeBetween15and30;
	}
	public Integer getCallTimeAbove60() {
		return callTimeAbove60;
	}
	public void setCallTimeAbove60(Integer callTimeAbove60) {
		this.callTimeAbove60 = callTimeAbove60;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}

package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 宜信致诚风险名单-社交关系网分析数据
 * @author sunda
 * @version 2017-07-10
 */
public class BehaviorFeatures implements Serializable {

	private static final long serialVersionUID = 8196355253246719419L;
	
	/**
	 *  性别
	 */
	private String gender;
	/**
	 *  性别码（M-男，F-女）
	 */
	private String genderCode;
	/**
	 *  年龄
	 */
	private String age;
	/**
	 * 身份证号归属地
	 */
	private String idNoLocation;
	/**
	 * 手机号码归属地
	 */
	private String mobileLocation;
	/**
	 * 手机运营商
	 */
	private String mobileOperator;
	/**
	 * 一阶联系人黑名单个数
	 */
	private String firstOrderBlackCnt;
	/**
	 * 一阶联系人逾期个数 ：当前逾期天数大于等于4天
	 */
	private String firstOrderOverdueCnt;
	/**
	 * 一阶联系人逾期 m3+个数:历史逾期天数大于等于 90 天，包含逾期还清
	 */
	private String firstOrderM3Cnt;
	/**
	 * 一 阶 联 系 人 黑 名 单 数 占 比 ：first_order_black_cnt*100/first_order_cnt
	 */
	private String firstOrderBlackRate; 
	/**
	 *  一 阶 联 系 人 逾 期 占 比 :first_order_overdue_cnt*100/first_order_cnt
	 */
	private String firstOrderOverdueRate; 
	/**
	 *  二阶联系人黑名单个数
	 */
	private String secondOrderBlackCnt; 
	/**
	 * 二阶联系人逾期个数
	 */
	private String secondOrderOverdueCnt; 
	/**
	 * 二阶联系人逾期 m3+个数:历史逾期天数大于等于 90 天，包含逾期还清
	 */
	private String secondOrderM3Cnt;  
	/**
	 * 主叫联系人数
	 */
	private String activeCallCnt;  
	/**
	 * 主叫联系人黑名单个数
	 */
	private String activeCallBlackCnt; 
	/**
	 * 主叫联系人逾期个数
	 */
	private String activeCallOverdueCnt; 
	/**
	 * 夜间通话人数：24 点到 5 点
	 */
	private String nightCallCnt; 
	/**
	 * 夜间通话次数：24 点到 5 点
	 */
	private String nightCallNum; 
	/**
	 * 夜间通话秒数：24 点到 5 点
	 */
	private String nightCallSeconds; 
	/**
	 * 与虚拟号码通话人数
	 */
	private String fictionCallCnt; 
	/**
	 * 与虚拟号码通话次数
	 */
	private String fictionCallNum; 
	/**
	 * 与虚拟号码通话秒数
	 */
	private String fictionCallSeconds; 
	/**
	 * 异地通话人数
	 */
	private String remoteCallCnt;  
	/**
	 * 异地通话次数
	 */
	private String remoteCallNum;  
	/**
	 * 异地通话秒数
	 */
	private String remoteCallSeconds;  
	/**
	 * 与澳门通话人数
	 */
	private String macaoCallCnt;  
	/**
	 * 与澳门通话次数
	 */
	private String macaoCallNum;  
	/**
	 * 与澳门通话秒数
	 */
	private String macaoCallSeconds; 
	/**
	 * 与银行或同行通话总次数
	 */
	private String loanCallNum; 
	/**
	 * 与银行或同行通话总秒数
	 */
	private String loanCallSeconds;  
	/**
	 * 与法院通话次数
	 */
	private String courtCallNum;  
	/**
	 * 与律师通话次数
	 */
	private String lawyerCallNum;  
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIdNoLocation() {
		return idNoLocation;
	}
	public void setIdNoLocation(String idNoLocation) {
		this.idNoLocation = idNoLocation;
	}
	public String getMobileLocation() {
		return mobileLocation;
	}
	public void setMobileLocation(String mobileLocation) {
		this.mobileLocation = mobileLocation;
	}
	public String getMobileOperator() {
		return mobileOperator;
	}
	public void setMobileOperator(String mobileOperator) {
		this.mobileOperator = mobileOperator;
	}
	public String getFirstOrderBlackCnt() {
		return firstOrderBlackCnt;
	}
	public void setFirstOrderBlackCnt(String firstOrderBlackCnt) {
		this.firstOrderBlackCnt = firstOrderBlackCnt;
	}
	public String getFirstOrderOverdueCnt() {
		return firstOrderOverdueCnt;
	}
	public void setFirstOrderOverdueCnt(String firstOrderOverdueCnt) {
		this.firstOrderOverdueCnt = firstOrderOverdueCnt;
	}
	public String getFirstOrderM3Cnt() {
		return firstOrderM3Cnt;
	}
	public void setFirstOrderM3Cnt(String firstOrderM3Cnt) {
		this.firstOrderM3Cnt = firstOrderM3Cnt;
	}
	public String getFirstOrderBlackRate() {
		return firstOrderBlackRate;
	}
	public void setFirstOrderBlackRate(String firstOrderBlackRate) {
		this.firstOrderBlackRate = firstOrderBlackRate;
	}
	public String getFirstOrderOverdueRate() {
		return firstOrderOverdueRate;
	}
	public void setFirstOrderOverdueRate(String firstOrderOverdueRate) {
		this.firstOrderOverdueRate = firstOrderOverdueRate;
	}
	public String getSecondOrderBlackCnt() {
		return secondOrderBlackCnt;
	}
	public void setSecondOrderBlackCnt(String secondOrderBlackCnt) {
		this.secondOrderBlackCnt = secondOrderBlackCnt;
	}
	public String getSecondOrderOverdueCnt() {
		return secondOrderOverdueCnt;
	}
	public void setSecondOrderOverdueCnt(String secondOrderOverdueCnt) {
		this.secondOrderOverdueCnt = secondOrderOverdueCnt;
	}
	public String getSecondOrderM3Cnt() {
		return secondOrderM3Cnt;
	}
	public void setSecondOrderM3Cnt(String secondOrderM3Cnt) {
		this.secondOrderM3Cnt = secondOrderM3Cnt;
	}
	public String getActiveCallCnt() {
		return activeCallCnt;
	}
	public void setActiveCallCnt(String activeCallCnt) {
		this.activeCallCnt = activeCallCnt;
	}
	public String getActiveCallBlackCnt() {
		return activeCallBlackCnt;
	}
	public void setActiveCallBlackCnt(String activeCallBlackCnt) {
		this.activeCallBlackCnt = activeCallBlackCnt;
	}
	public String getActiveCallOverdueCnt() {
		return activeCallOverdueCnt;
	}
	public void setActiveCallOverdueCnt(String activeCallOverdueCnt) {
		this.activeCallOverdueCnt = activeCallOverdueCnt;
	}
	public String getNightCallCnt() {
		return nightCallCnt;
	}
	public void setNightCallCnt(String nightCallCnt) {
		this.nightCallCnt = nightCallCnt;
	}
	public String getNightCallNum() {
		return nightCallNum;
	}
	public void setNightCallNum(String nightCallNum) {
		this.nightCallNum = nightCallNum;
	}
	public String getNightCallSeconds() {
		return nightCallSeconds;
	}
	public void setNightCallSeconds(String nightCallSeconds) {
		this.nightCallSeconds = nightCallSeconds;
	}
	public String getFictionCallCnt() {
		return fictionCallCnt;
	}
	public void setFictionCallCnt(String fictionCallCnt) {
		this.fictionCallCnt = fictionCallCnt;
	}
	public String getFictionCallNum() {
		return fictionCallNum;
	}
	public void setFictionCallNum(String fictionCallNum) {
		this.fictionCallNum = fictionCallNum;
	}
	public String getFictionCallSeconds() {
		return fictionCallSeconds;
	}
	public void setFictionCallSeconds(String fictionCallSeconds) {
		this.fictionCallSeconds = fictionCallSeconds;
	}
	public String getRemoteCallCnt() {
		return remoteCallCnt;
	}
	public void setRemoteCallCnt(String remoteCallCnt) {
		this.remoteCallCnt = remoteCallCnt;
	}
	public String getRemoteCallNum() {
		return remoteCallNum;
	}
	public void setRemoteCallNum(String remoteCallNum) {
		this.remoteCallNum = remoteCallNum;
	}
	public String getRemoteCallSeconds() {
		return remoteCallSeconds;
	}
	public void setRemoteCallSeconds(String remoteCallSeconds) {
		this.remoteCallSeconds = remoteCallSeconds;
	}
	public String getMacaoCallCnt() {
		return macaoCallCnt;
	}
	public void setMacaoCallCnt(String macaoCallCnt) {
		this.macaoCallCnt = macaoCallCnt;
	}
	public String getMacaoCallNum() {
		return macaoCallNum;
	}
	public void setMacaoCallNum(String macaoCallNum) {
		this.macaoCallNum = macaoCallNum;
	}
	public String getMacaoCallSeconds() {
		return macaoCallSeconds;
	}
	public void setMacaoCallSeconds(String macaoCallSeconds) {
		this.macaoCallSeconds = macaoCallSeconds;
	}
	public String getLoanCallNum() {
		return loanCallNum;
	}
	public void setLoanCallNum(String loanCallNum) {
		this.loanCallNum = loanCallNum;
	}
	public String getLoanCallSeconds() {
		return loanCallSeconds;
	}
	public void setLoanCallSeconds(String loanCallSeconds) {
		this.loanCallSeconds = loanCallSeconds;
	}
	public String getCourtCallNum() {
		return courtCallNum;
	}
	public void setCourtCallNum(String courtCallNum) {
		this.courtCallNum = courtCallNum;
	}
	public String getLawyerCallNum() {
		return lawyerCallNum;
	}
	public void setLawyerCallNum(String lawyerCallNum) {
		this.lawyerCallNum = lawyerCallNum;
	}

}
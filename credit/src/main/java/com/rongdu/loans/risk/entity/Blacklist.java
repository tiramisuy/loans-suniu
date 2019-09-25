/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.sql.Date;

/**
 * 风控黑名单Entity
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class Blacklist extends BaseEntity<Blacklist> {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 证件号码
	 */
	private String idNo;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 座机号码
	 */
	private String telphone;
	/**
	 * qq号码
	 */
	private String qq;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 风险分类
	 */
	private String riskCode;
	/**
	 * 风险名称
	 */
	private String riskName;
	/**
	 * 来源类型：0-网贷行业公布的黑名单;1-在平台有欠息或有不良贷款; 2-提供虚假材料或拒绝接受检查;3-命中合作机构的黑名单；4-网络黑名单
	 */
	private String sourceType;
	/**
	 * 来源机构
	 */
	private String sourceOrg;
	/**
	 * 贷款逾期期数
	 */
	private String overduePeriod;
	/**
	 * 贷款逾期金额
	 */
	private String overdueAmt;
	/**
	 * 进出黑名单的时间
	 */
	private Date time;
	/**
	 * 进出黑名单原因
	 */
	private String reason;
	/**
	 * 扩展信息
	 */
	private String extendInfo;
	/**
	 * 黑名单状态：0-预登记;1-生效;2-否决;3-注销
	 */
	private Integer status;

	public Blacklist() {
		super();
	}

	public Blacklist(String id) {
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceOrg() {
		return sourceOrg;
	}

	public void setSourceOrg(String sourceOrg) {
		this.sourceOrg = sourceOrg;
	}

	public String getOverduePeriod() {
		return overduePeriod;
	}

	public void setOverduePeriod(String overduePeriod) {
		this.overduePeriod = overduePeriod;
	}

	public String getOverdueAmt() {
		return overdueAmt;
	}

	public void setOverdueAmt(String overdueAmt) {
		this.overdueAmt = overdueAmt;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
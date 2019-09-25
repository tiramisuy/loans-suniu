/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-风险项记录Entity
 * @author sunda
 * @version 2017-08-14
 */
public class EchoRiskResult extends BaseEntity<EchoRiskResult> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *提供数据的机构代号
	  */
	private String orgName;		
	/**
	  *命中项码
	  */
	private String riskItemTypeCode;		
	/**
	  *命中内容
	  */
	private String riskItemValue;		
	/**
	  *风险类别码
	  */
	private String riskTypeCode;		
	/**
	  *风险明细
	  */
	private String riskDetail;		
	/**
	  *风险最近时间
	  */
	private String riskTime;		
	/**
	  *流水号
	  */
	private String flowId;		
	
	public EchoRiskResult() {
		super();
	}

	public EchoRiskResult(String id){
		super(id);
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
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getRiskItemTypeCode() {
		return riskItemTypeCode;
	}

	public void setRiskItemTypeCode(String riskItemTypeCode) {
		this.riskItemTypeCode = riskItemTypeCode;
	}
	
	public String getRiskItemValue() {
		return riskItemValue;
	}

	public void setRiskItemValue(String riskItemValue) {
		this.riskItemValue = riskItemValue;
	}
	
	public String getRiskTypeCode() {
		return riskTypeCode;
	}

	public void setRiskTypeCode(String riskTypeCode) {
		this.riskTypeCode = riskTypeCode;
	}
	
	public String getRiskDetail() {
		return riskDetail;
	}

	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}
	
	public String getRiskTime() {
		return riskTime;
	}

	public void setRiskTime(String riskTime) {
		this.riskTime = riskTime;
	}
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
}
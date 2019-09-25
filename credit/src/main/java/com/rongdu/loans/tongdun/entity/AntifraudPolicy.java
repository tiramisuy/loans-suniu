/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 同盾-反欺诈策略Entity
 * @author sunda
 * @version 2017-08-14
 */
public class AntifraudPolicy extends BaseEntity<AntifraudPolicy> {
	
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
	  *策略uuid
	  */
	private String policyUuid;		
	/**
	  *策略结果
	  */
	private String policyDecision;		
	/**
	  *策略模式
	  */
	private String policyMode;		
	/**
	  *策略名称
	  */
	private String policyName;		
	/**
	  *策略分数
	  */
	private Integer policyScore;		
	/**
	  *风险类型
	  */
	private String riskType;		
	/**
	  *请求ID
	  */
	private String seqId;		
	
	public AntifraudPolicy() {
		super();
	}

	public AntifraudPolicy(String id){
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
	
	public String getPolicyUuid() {
		return policyUuid;
	}

	public void setPolicyUuid(String policyUuid) {
		this.policyUuid = policyUuid;
	}
	
	public String getPolicyDecision() {
		return policyDecision;
	}

	public void setPolicyDecision(String policyDecision) {
		this.policyDecision = policyDecision;
	}
	
	public String getPolicyMode() {
		return policyMode;
	}

	public void setPolicyMode(String policyMode) {
		this.policyMode = policyMode;
	}
	
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	
	public Integer getPolicyScore() {
		return policyScore;
	}

	public void setPolicyScore(Integer policyScore) {
		this.policyScore = policyScore;
	}
	
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
}
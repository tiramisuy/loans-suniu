/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 同盾-反欺诈决策服务Entity
 * @author sunda
 * @version 2017-08-14
 */
public class AntifraudApi extends BaseEntity<AntifraudApi> {
	
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
	  *风险分数
	  */
	private Integer finalScore;		
	/**
	  *最终的风险决策结果
	  */
	private String finalDecision;		
	/**
	  *策略名称
	  */
	private String policyName;		
	/**
	  *花费的时间，单位ms
	  */
	private Integer spendTime;		
	/**
	  *策略集名称
	  */
	private String policySetName;		
	/**
	  *风险类型
	  */
	private String riskType;		
	
	public AntifraudApi() {
		super();
	}

	public AntifraudApi(String id){
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
	
	public Integer getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
	
	public String getFinalDecision() {
		return finalDecision;
	}

	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}
	
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	
	public Integer getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(Integer spendTime) {
		this.spendTime = spendTime;
	}
	
	public String getPolicySetName() {
		return policySetName;
	}

	public void setPolicySetName(String policySetName) {
		this.policySetName = policySetName;
	}
	
	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
}
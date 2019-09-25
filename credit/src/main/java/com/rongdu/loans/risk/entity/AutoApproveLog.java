/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 自动审批日志Entity
 * @author sunda
 * @version 2017-08-27
 */
public class AutoApproveLog extends BaseEntity<AutoApproveLog> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *用户姓名
	  */
	private String name;		
	/**
	  *贷款申请编号
	  */
	private String applyId;		
	/**
	  *自动审批ID
	  */
	private String autoApproveId;		
	/**
	  *策略ID
	  */
	private String ruleId;		
	/**
	  *策略名称
	  */
	private String ruleName;		
	/**
	  *耗时（毫秒）
	  */
	private Integer costTime;		
	/**
	  *命中规则数量
	  */
	private Integer hitNum;		
	/**
	  *欺诈分
	  */
	private Integer score;		
	/**
	  *审批决策：ACCEPT-通过，REVIEW-人工审批，REJECT-拒绝
	  */
	private String decision;		
	/**
	  *审核时间
	  */
	private Date approveTime;
	/**
	  *执行状态：SUCCESS-成功，FAILURE-失败
	  */
	private String status;		
	
	public AutoApproveLog() {
		super();
	}

	public AutoApproveLog(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getAutoApproveId() {
		return autoApproveId;
	}

	public void setAutoApproveId(String autoApproveId) {
		this.autoApproveId = autoApproveId;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public Integer getCostTime() {
		return costTime;
	}

	public void setCostTime(Integer costTime) {
		this.costTime = costTime;
	}
	
	public Integer getHitNum() {
		return hitNum;
	}

	public void setHitNum(Integer hitNum) {
		this.hitNum = hitNum;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
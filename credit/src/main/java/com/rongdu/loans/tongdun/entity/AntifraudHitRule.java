/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 同盾-反欺诈命中规则Entity
 * @author sunda
 * @version 2017-08-14
 */
public class AntifraudHitRule extends BaseEntity<AntifraudHitRule> {
	
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
	  *规则UUID
	  */
	private String uuid;		
	/**
	  *规则编号
	  */
	private String ruleId;		
	/**
	  *规则名称
	  */
	private String name;		
	/**
	  *该条规则决策结果
	  */
	private String decision;		
	/**
	  *策略集名称
	  */
	private Integer score;		
	/**
	  *上级规则UUID
	  */
	private String parentUuid;		
	/**
	  *请求序列号，每个请求进来都分配一个全局唯一的id
	  */
	private String seqId;		
	
	public AntifraudHitRule() {
		super();
	}

	public AntifraudHitRule(String id){
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
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}
	
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
}
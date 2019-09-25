/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 命中的风控规则Entity
 * @author sunda
 * @version 2017-08-27
 */
public class HitRule extends BaseEntity<HitRule> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *上级规则代码
	  */
	private String parentRuleId;		
	/**
	  *风险类型代码（risk_category的level为2时的风险）
	  */
	private String riskTypeCode;		
	/**
	  *风险名称
	  */
	private String riskTypeName;		
	/**
	  *规则来源
	  */
	private String source;		
	/**
	  *姓名
	  */
	private String name;		
	/**
	  *规则代码，默认为外部规则代码；如果为空，则同rule_id
	  */
	private String ruleCode;		
	/**
	  *规则名称
	  */
	private String ruleName;		
	/**
	  *风险等级：A-拒绝+进入黑名单，B-拒绝，C-定义分值，人工审核，D-定义分值，人工审核
	  */
	private String riskRank;		
	/**
	  *分值
	  */
	private Integer score;		
	/**
	  *风险决策
	  */
	private String decision;		
	/**
	  *排列顺序
	  */
	private Integer sort;		
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *申请编号
	  */
	private String applyId;		
	/**
	  *风控规则ID
	  */
	private String ruleId;		
	/**
	  *取值
	  */
	private String value;		
	
	public HitRule() {
		super();
	}

	public HitRule(String id){
		super(id);
	}

	public String getParentRuleId() {
		return parentRuleId;
	}

	public void setParentRuleId(String parentRuleId) {
		this.parentRuleId = parentRuleId;
	}
	
	public String getRiskTypeCode() {
		return riskTypeCode;
	}

	public void setRiskTypeCode(String riskTypeCode) {
		this.riskTypeCode = riskTypeCode;
	}
	
	public String getRiskTypeName() {
		return riskTypeName;
	}

	public void setRiskTypeName(String riskTypeName) {
		this.riskTypeName = riskTypeName;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public String getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(String riskRank) {
		this.riskRank = riskRank;
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
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
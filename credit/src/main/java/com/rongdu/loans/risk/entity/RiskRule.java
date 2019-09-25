/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 风控规则Entity
 * @author sunda
 * @version 2017-08-27
 */
public class RiskRule extends BaseEntity<RiskRule> {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 模型id
	 */
	private Integer modelId;
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
	  *规则代码，默认为外部规则代码；如果为空，则同rule_id
	  */
	private String ruleCode;		
	/**
	  *上送规则引擎字段名称
	  */
	private String fieldName;		
	/**
	  *规则名称
	  */
	private String ruleName;		
	/**
	  *规则描述
	  */
	private String ruleDesc;		
	/**
	  *阈值
	  */
	private String threshold;		
	/**
	  *风险等级：A-拒绝+进入黑名单，B-拒绝，C-定义分值，人工审核，D-定义分值，人工审核
	  */
	private String riskRank;		
	/**
	  *分值
	  */
	private Integer score;		
	/**
	  *风险规则状态：OFF-禁用，ON-开启
	  */
	private String status;		
	/**
	  *排列顺序
	  */
	private Integer sort;		
	
	public RiskRule() {
		super();
	}

	public RiskRule(String id){
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
	
	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
}
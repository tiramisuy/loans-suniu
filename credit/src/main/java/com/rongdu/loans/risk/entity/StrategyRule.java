/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 风控策略与风控规则的对应关系Entity
 * @author sunda
 * @version 2017-08-27
 */
public class StrategyRule extends BaseEntity<StrategyRule> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *策略ID
	  */
	private String strategyId;		
	/**
	  *规则ID
	  */
	private String ruleId;		
	/**
	  *风险规则状态：OFF-禁用，ON-开启
	  */
	private String status;		
	
	public StrategyRule() {
		super();
	}

	public StrategyRule(String id){
		super(id);
	}

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
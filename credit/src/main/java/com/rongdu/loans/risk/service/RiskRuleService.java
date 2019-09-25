/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.loans.risk.entity.RiskRule;

import java.util.List;
import java.util.Map;

/**
 * 风控规则-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface RiskRuleService {
	
	/**
	 * 获得所有的风控规则列表
	 * @return
	 */
	public List<RiskRule> getAllRiskRuleList();

	/**
	 * 获得某个特定的风控规则
	 * @param ruleCode
	 * @return
	 */
	public RiskRule getRiskRule(String ruleCode,Integer modelId);

	/**
	 * 根据来源查询一组风控规则
	 * @param ruleSetId 不能为空
	 * @return
	 */
	public List<RiskRule> getRiskRuleList(String ruleSetId,Integer modelId) ;

	/**
	 * 根据来源查询一组风控规则
	 * @param ruleSetId 不能为空
	 * @return
	 */
	public Map<String, RiskRule> getRiskRuleMap(String ruleSetId,Integer modelId);

}
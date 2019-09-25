/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.RiskRuleDao;
import com.rongdu.loans.risk.entity.RiskRule;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风控规则-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("riskRuleManager")
public class RiskRuleManager extends BaseManager<RiskRuleDao, RiskRule, String>{
	
	/**
	 * 获得所有的风控规则列表
	 */
	public List<RiskRule> getAllRiskRuleList() {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", "ON"));
		List<RiskRule> list = findAllByCriteria(criteria);
		return list;
	}

	/**
	 * 获得某个特定的风控规则
	 */
	public RiskRule getRiskRule(String ruleId) {
		
		return null;
	}
	
}
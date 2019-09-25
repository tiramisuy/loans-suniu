/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.StrategyRuleDao;
import com.rongdu.loans.risk.entity.StrategyRule;
import org.springframework.stereotype.Service;

/**
 * 风控策略与风控规则的对应关系-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("strategyRuleManager")
public class StrategyRuleManager extends BaseManager<StrategyRuleDao, StrategyRule, String>{
	
}
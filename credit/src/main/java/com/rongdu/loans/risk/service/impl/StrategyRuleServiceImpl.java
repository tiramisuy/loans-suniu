/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.risk.manager.StrategyRuleManager;
import com.rongdu.loans.risk.service.StrategyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 风控策略与风控规则的对应关系-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("strategyRuleService")
public class StrategyRuleServiceImpl  extends BaseService implements  StrategyRuleService{
	
	/**
 	* 风控策略与风控规则的对应关系-实体管理接口
 	*/
	@Autowired
	private StrategyRuleManager strategyRuleManager;
	
}
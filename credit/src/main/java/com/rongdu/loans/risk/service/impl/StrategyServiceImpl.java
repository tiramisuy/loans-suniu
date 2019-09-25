/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.risk.manager.StrategyManager;
import com.rongdu.loans.risk.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 风控策略-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("strategyService")
public class StrategyServiceImpl  extends BaseService implements  StrategyService{
	
	/**
 	* 风控策略-实体管理接口
 	*/
	@Autowired
	private StrategyManager strategyManager;
	
}
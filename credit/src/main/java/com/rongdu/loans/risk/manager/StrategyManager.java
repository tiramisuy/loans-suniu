/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.StrategyDao;
import com.rongdu.loans.risk.entity.Strategy;
import org.springframework.stereotype.Service;

/**
 * 风控策略-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("strategyManager")
public class StrategyManager extends BaseManager<StrategyDao, Strategy, String>{
	
}
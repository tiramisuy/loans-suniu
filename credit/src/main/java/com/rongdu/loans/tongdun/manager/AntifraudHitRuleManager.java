/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tongdun.dao.AntifraudHitRuleDao;
import com.rongdu.loans.tongdun.entity.AntifraudHitRule;
import org.springframework.stereotype.Service;

/**
 * 同盾-反欺诈命中规则-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("antifraudHitRuleManager")
public class AntifraudHitRuleManager extends BaseManager<AntifraudHitRuleDao, AntifraudHitRule, String>{
	
}
/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.tongdun.manager.AntifraudHitRuleManager;
import com.rongdu.loans.tongdun.service.AntifraudHitRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 同盾-反欺诈命中规则-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("antifraudHitRuleService")
public class AntifraudHitRuleServiceImpl  extends BaseService implements  AntifraudHitRuleService{
	
	/**
 	* 同盾-反欺诈命中规则-实体管理接口
 	*/
	@Autowired
	private AntifraudHitRuleManager antifraudHitRuleManager;
	
}
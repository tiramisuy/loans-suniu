/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.manager.HitRuleManager;
import com.rongdu.loans.risk.service.HitRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 命中的风控规则-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("hitRuleService")
public class HitRuleServiceImpl  extends BaseService implements  HitRuleService{
	
	/**
 	* 命中的风控规则-实体管理接口
 	*/
	@Autowired
	private HitRuleManager hitRuleManager;
	
	/**
	 * 保存命中的规则
	 * @param rule
	 * @return
	 */
	public int insert(HitRule rule){
		return hitRuleManager.insert(rule);
	}
	
	/**
	 * 批量保存命中的规则
	 * @param list
	 * @return
	 */
	public int insertBatch(List<HitRule> list){
		return hitRuleManager.insertBatch(list);
	}

	/**
	 * 按照applyId或者userId来查询命中的风控规则
	 * @param applyId
	 * @param userId
	 * @return
	 */
	public List<HitRule> getHitRuleList(String applyId,String userId){
		return hitRuleManager.getHitRuleList(applyId,userId);
	}
	
}
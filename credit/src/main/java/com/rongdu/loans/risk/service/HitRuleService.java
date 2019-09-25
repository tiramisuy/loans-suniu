/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;


/**
 * 命中的风控规则-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface HitRuleService {
	
	/**
	 * 保存命中的规则
	 * @param rule
	 * @return
	 */
	public int insert(HitRule rule); 
	
	/**
	 * 批量保存命中的规则
	 * @param rule
	 * @return
	 */
	public int insertBatch(List<HitRule> list);

	/**
	 * 按照applyId或者userId来查询命中的风控规则
	 * @param applyId
	 * @param userId
     * @return
     */
	public List<HitRule> getHitRuleList(String applyId,String userId);

}
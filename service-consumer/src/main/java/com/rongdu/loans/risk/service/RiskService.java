/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.loans.risk.vo.HitRuleVO;

import java.util.List;


/**
 * 风控规则-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface RiskService {

	/**
	 * 按照applyId或者userId来查询命中的风控规则
	 * @param applyId
	 * @param userId
     * @return
     */
	public List<HitRuleVO> getHitRuleList(String applyId, String userId);

	/**
	 * 根据至简提供宜信阿福上报
	 * @param applyId
	 * @param userId
	 * @param riskRankList
	 * @return
	 */
	public List<HitRuleVO> getHitRuleList(String applyId, String userId, List<String> riskRankList);

}
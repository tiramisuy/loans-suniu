/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.manager.HitRuleManager;
import com.rongdu.loans.risk.service.RiskService;
import com.rongdu.loans.risk.vo.HitRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 风控管理-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("riskService")
public class RiskServiceImpl extends BaseService implements RiskService {
	
	/**
 	* 风控规则-实体管理接口
 	*/
	@Autowired
	private HitRuleManager hitRuleManager;

	/**
	 * 按照applyId或者userId来查询命中的风控规则
	 * @param applyId
	 * @param userId
	 * @return
	 */
	public List<HitRuleVO> getHitRuleList(String applyId, String userId){
		List<HitRule> list = hitRuleManager.getHitRuleList(applyId,userId);
		List<HitRuleVO> volist = new ArrayList<>();
		HitRuleVO vo = null;
		if (list!=null&&!list.isEmpty()){
			for (HitRule item:list){
				vo = BeanMapper.map(item,HitRuleVO.class);
				vo.setRiskType(item.getRiskTypeCode());
				vo.setRiskName(item.getRiskTypeName());
				volist.add(vo);

			}
		}
		return volist;
	}

	@Override
	public List<HitRuleVO> getHitRuleList(String applyId, String userId, List<String> riskRankList) {
		Criteria criteria = new Criteria();
		if(StringUtils.isNotBlank(userId)){
			criteria.and(Criterion.eq("user_id",userId));
		}
		if(StringUtils.isNotBlank(applyId)){
			criteria.and(Criterion.eq("apply_id",applyId));
		}
		if(CollectionUtils.isNotEmpty(riskRankList)){
			criteria.and(Criterion.in("risk_rank", riskRankList));
		}
		List<HitRule> list = hitRuleManager.findAllByCriteria(criteria);

		List<HitRuleVO> volist = new ArrayList<>();
		HitRuleVO vo = null;
		if (list!=null&&!list.isEmpty()){
			for (HitRule item:list){
				vo = BeanMapper.map(item,HitRuleVO.class);
				vo.setRiskType(item.getRiskTypeCode());
				vo.setRiskName(item.getRiskTypeName());
				volist.add(vo);

			}
		}
		return volist;
	}
}
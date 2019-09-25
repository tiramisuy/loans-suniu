/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.entity.ApplyTripartiteAnrong;
import com.rongdu.loans.loan.manager.ApplyTripartiteAnrongManager;
import com.rongdu.loans.loan.service.ApplyTripartiteAnrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工单映射（安融）-业务逻辑实现类
 * @author fy
 * @version 2019-06-21
 */
@Service("applyTripartiteAnrongService")
public class ApplyTripartiteAnrongServiceImpl  extends BaseService implements  ApplyTripartiteAnrongService{
	
	/**
 	* 工单映射（安融）-实体管理接口
 	*/
	@Autowired
	private ApplyTripartiteAnrongManager applyTripartiteAnrongManager;

	@Override
	public void save(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		long l = applyTripartiteAnrongManager.countByCriteria(criteria);
		if (l > 0){
			return;
		}
		ApplyTripartiteAnrong entity = new ApplyTripartiteAnrong();
		entity.setApplyId(applyId);
		entity.setTripartiteNo(applyId);
		applyTripartiteAnrongManager.insert(entity);
	}

	@Override
	public void update(String loanId, String checkResult) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", loanId));
		ApplyTripartiteAnrong entity = applyTripartiteAnrongManager.getByCriteria(criteria);
		if (entity != null){
			entity.setStatus(checkResult);
			applyTripartiteAnrongManager.update(entity);
		}
	}

	@Override
	public boolean isExistApplyId(String applyId,String status) {
		if (applyId == null) {
			applyId = "";
		}
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		criteria.and(Criterion.eq("del", 0));
		if (StringUtils.isNotBlank(status)){
			criteria.and(Criterion.eq("status", status));
		}
		long count = applyTripartiteAnrongManager.countByCriteria(criteria);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> findThirdIdsByApplyIds(List<String> applyIds) {
		return applyTripartiteAnrongManager.findThirdIdsByApplyIds(applyIds);
	}
}
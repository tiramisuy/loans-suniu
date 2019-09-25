/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.ApplyTripartiteRong360Dao;
import com.rongdu.loans.loan.entity.ApplyTripartiteRong360;

/**
 * 工单映射（融360）-实体管理实现类
 * @author yuanxianchu
 * @version 2018-06-29
 */
@Service("applyTripartiteRong360Manager")
public class ApplyTripartiteRong360Manager extends BaseManager<ApplyTripartiteRong360Dao, ApplyTripartiteRong360, String> {
	
	@Autowired
	ApplyTripartiteRong360Dao applyTripartiteRong360Dao;
	
	public List<String> findThirdIdsByApplyIds(List<String> applyIds){
		return applyTripartiteRong360Dao.findThirdIdsByApplyIds(applyIds);
	}
}
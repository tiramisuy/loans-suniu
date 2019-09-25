/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.ApplyTripartite;
import com.rongdu.loans.loan.dao.ApplyTripartiteDao;
import com.rongdu.loans.loan.dao.ApplyTripartiteRong360Dao;

/**
 * 工单映射-实体管理实现类
 * @author Lee
 * @version 2018-05-29
 */
@Service("applyTripartiteManager")
public class ApplyTripartiteManager extends BaseManager<ApplyTripartiteDao, ApplyTripartite, String> {
	
	@Autowired
	ApplyTripartiteDao applyTripartiteDao;
	
	public List<String> findThirdIdsByApplyIds(List<String> applyIds){
		return applyTripartiteDao.findThirdIdsByApplyIds(applyIds);
	}
}
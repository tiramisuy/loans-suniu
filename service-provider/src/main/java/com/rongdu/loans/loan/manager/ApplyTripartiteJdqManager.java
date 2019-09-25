/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.ApplyTripartiteJdqDao;
import com.rongdu.loans.loan.entity.ApplyTripartiteJdq;

/**
 * 工单映射（借点钱）-实体管理实现类
 *
 * @author Lee
 * @version 2018-10-12
 */
@Service("applyTripartiteJdqManager")
public class ApplyTripartiteJdqManager extends BaseManager<ApplyTripartiteJdqDao, ApplyTripartiteJdq, String> {

	
	public List<String> findThirdIdsByApplyIds(List<String> applyIds){
		return dao.findThirdIdsByApplyIds(applyIds);
	}

}
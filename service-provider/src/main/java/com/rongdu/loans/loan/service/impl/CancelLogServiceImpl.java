/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.loans.loan.dao.CancelLogDao;
import com.rongdu.loans.loan.option.CancelLog;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.service.CancelLogService;
/**
 * loan_cancel_log-业务逻辑实现类
 * @author qf
 * @version 2019-02-26
 */
@Service("cancelLogService")
public class CancelLogServiceImpl  extends BaseService implements  CancelLogService{
	
	@Autowired
	private CancelLogDao cancelLogDao;
	@Override
	public int saveCancelLog(CancelLog cancelLog) {
		return cancelLogDao.insert(cancelLog);
	}

	@Override
	public long countByUserId(String userId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("user_id", userId));
		criteria.and(Criterion.eq("del", 0));
		return cancelLogDao.countByCriteria(criteria);
	}
}
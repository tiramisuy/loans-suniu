/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.manager.OverdueCountManager;
import com.rongdu.loans.loan.option.OverdueCountOP;
import com.rongdu.loans.loan.service.OverdueCountService;
import com.rongdu.loans.loan.vo.OverdueCountVO;

/**
 * 还款计划明细-业务逻辑实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-08
 */
@Service("overdueCountService")
public class OverdueCountServiceImpl extends BaseService implements OverdueCountService {
	
	@Autowired
	private OverdueCountManager overdueCountManager;
	
	
	@Override
	public Page<OverdueCountVO> overdueCountList(OverdueCountOP op) {
		Page<OverdueCountVO> voPage = new Page<OverdueCountVO>(op.getPageNo(),
				op.getPageSize());
		List<OverdueCountVO> voList = (List<OverdueCountVO>) overdueCountManager
				.overdueCountList(voPage, op);
		voPage.setList(voList);
		return voPage;

	}
}
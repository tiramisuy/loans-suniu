/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.zhicheng.entity.EchoDecisionReport;
import com.rongdu.loans.zhicheng.manager.EchoDecisionReportManager;
import com.rongdu.loans.zhicheng.service.EchoDecisionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 致诚信用-阿福共享平台-综合决策报告记录-业务逻辑实现类
 * @author fy
 * @version 2019-07-05
 */
@Service("echoDecisionReportService")
public class EchoDecisionReportServiceImpl  extends BaseService implements  EchoDecisionReportService{
	
	/**
 	* 致诚信用-阿福共享平台-综合决策报告记录-实体管理接口
 	*/
	@Autowired
	private EchoDecisionReportManager echoDecisionReportManager;

	@Override
	public void saveDecisionReport(EchoDecisionReport entity) {
		EchoDecisionReport decisionReport = getDecisionReport(entity.getApplyId());
		if(null == decisionReport){
			echoDecisionReportManager.insert(entity);
		}
	}

	@Override
	public EchoDecisionReport getDecisionReport(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return echoDecisionReportManager.getByCriteria(criteria);
	}
}
/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreenRiskResult;
import com.rongdu.loans.zhicheng.manager.EchoFraudScreenRiskResultManager;
import com.rongdu.loans.zhicheng.service.EchoFraudScreenRiskResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 致诚信用-阿福共享平台-欺诈甄别-风险名单结果集记录(由于不是直接调用,数据不全,后期谨慎修改)-业务逻辑实现类
 * @author fy
 * @version 2019-07-05
 */
@Service("echoFraudScreenRiskResultService")
public class EchoFraudScreenRiskResultServiceImpl  extends BaseService implements  EchoFraudScreenRiskResultService{
	
	/**
 	* 致诚信用-阿福共享平台-欺诈甄别-风险名单结果集记录(由于不是直接调用,数据不全,后期谨慎修改)-实体管理接口
 	*/
	@Autowired
	private EchoFraudScreenRiskResultManager echoFraudScreenRiskResultManager;

	@Override
	public void saveFraudScreenRiskResultList(List<EchoFraudScreenRiskResult> entityList) {
		echoFraudScreenRiskResultManager.insertBatch(entityList);
	}

	@Override
	public List<EchoFraudScreenRiskResult> getFraudScreenRiskResult(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return echoFraudScreenRiskResultManager.findAllByCriteria(criteria);
	}
}
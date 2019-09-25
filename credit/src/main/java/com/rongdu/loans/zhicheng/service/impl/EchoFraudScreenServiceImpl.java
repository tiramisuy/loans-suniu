/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreen;
import com.rongdu.loans.zhicheng.manager.EchoFraudScreenManager;
import com.rongdu.loans.zhicheng.service.EchoFraudScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 致诚信用-阿福共享平台-欺诈甄别记录-业务逻辑实现类
 * @author fy
 * @version 2019-07-05
 */
@Service("echoFraudScreenService")
public class EchoFraudScreenServiceImpl  extends BaseService implements  EchoFraudScreenService{
	
	/**
 	* 致诚信用-阿福共享平台-欺诈甄别记录-实体管理接口
 	*/
	@Autowired
	private EchoFraudScreenManager echoFraudScreenManager;

	@Override
	public void saveFraudScreen(EchoFraudScreen echoFraudScreen) {
		EchoFraudScreen fraudScreen = getFraudScreen(echoFraudScreen.getApplyId());
		if (fraudScreen == null){
			echoFraudScreenManager.insert(echoFraudScreen);
		}
	}

	@Override
	public EchoFraudScreen getFraudScreen(String applyId) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("apply_id", applyId));
		return echoFraudScreenManager.getByCriteria(criteria);
	}
}
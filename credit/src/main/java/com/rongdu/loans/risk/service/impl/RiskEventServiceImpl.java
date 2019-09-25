/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.risk.manager.RiskEventManager;
import com.rongdu.loans.risk.service.RiskEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 风险事件：指风险在什么时候发生，如：注册、登录、绑卡、贷款、提现等-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("riskEventService")
public class RiskEventServiceImpl  extends BaseService implements  RiskEventService{
	
	/**
 	* 风险事件：指风险在什么时候发生，如：注册、登录、绑卡、贷款、提现等-实体管理接口
 	*/
	@Autowired
	private RiskEventManager riskEventManager;
	
}
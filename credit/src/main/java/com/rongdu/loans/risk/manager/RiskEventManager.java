/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.RiskEventDao;
import com.rongdu.loans.risk.entity.RiskEvent;
import org.springframework.stereotype.Service;

/**
 * 风险事件：指风险在什么时候发生，如：注册、登录、绑卡、贷款、提现等-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("riskEventManager")
public class RiskEventManager extends BaseManager<RiskEventDao, RiskEvent, String>{
	
}
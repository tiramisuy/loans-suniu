/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.risk.entity.RiskEvent;

/**
 * 风险事件：指风险在什么时候发生，如：注册、登录、绑卡、贷款、提现等-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface RiskEventDao extends BaseDao<RiskEvent,String> {
	
}
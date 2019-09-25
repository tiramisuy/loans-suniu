/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.tongdun.entity.AntifraudPolicy;

/**
 * 同盾-反欺诈策略-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface AntifraudPolicyDao extends BaseDao<AntifraudPolicy,String> {
	
}
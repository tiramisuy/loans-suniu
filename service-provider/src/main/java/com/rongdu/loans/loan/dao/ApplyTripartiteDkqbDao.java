/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ApplyTripartiteDkqb;

/**
 * 工单映射（贷款钱包）-数据访问接口
 * @author fy
 * @version 2019-04-16
 */
@MyBatisDao
public interface ApplyTripartiteDkqbDao extends BaseDao<ApplyTripartiteDkqb,String> {
	
}
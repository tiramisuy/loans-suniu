/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.BindCard;

/**
 * 绑卡信息-数据访问接口
 * @author sunda
 * @version 2017-07-21
 */
@MyBatisDao
public interface BindCardDao extends BaseDao<BindCard,String> {
	
}
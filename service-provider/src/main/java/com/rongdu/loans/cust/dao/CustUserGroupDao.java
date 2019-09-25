/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.CustUserGroup;

/**
 * 用户分组表-数据访问接口
 * @author liuzhuang
 * @version 2018-04-18
 */
@MyBatisDao
public interface CustUserGroupDao extends BaseDao<CustUserGroup,String> {
	
}
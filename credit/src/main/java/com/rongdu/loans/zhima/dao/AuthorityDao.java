/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhima.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.zhima.entity.Authority;

/**
 * 芝麻信用授权-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface AuthorityDao extends BaseDao<Authority,String> {
	
}
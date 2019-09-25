/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.UserMd5;


/**
 * 用户md5-数据访问接口
 * @author Lee
 * @version 2018-07-09
 */
@MyBatisDao
public interface UserMd5Dao extends BaseDao<UserMd5,String> {
	
}
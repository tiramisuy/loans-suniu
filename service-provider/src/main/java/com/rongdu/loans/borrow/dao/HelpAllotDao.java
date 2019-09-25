/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.borrow.entity.HelpAllot;

/**
 * 助贷产品分配表-数据访问接口
 * @author liuliang
 * @version 2018-08-29
 */
@MyBatisDao
public interface HelpAllotDao extends BaseDao<HelpAllot,String> {
	
}
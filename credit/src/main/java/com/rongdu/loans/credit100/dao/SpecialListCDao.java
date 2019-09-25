/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit100.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.credit100.entity.SpecialListC;

/**
 * 百融-特殊名单核查-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface SpecialListCDao extends BaseDao<SpecialListC,String> {
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.credit.entity.ApiInvokeLog;

/**
 * 征信数据合作机构接口调用日志-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface ApiInvokeLogDao extends BaseDao<ApiInvokeLog,String> {
	
}
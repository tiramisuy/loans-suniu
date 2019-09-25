/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.app.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.app.entity.AccessLog;

/**
 * APP访问日志-数据访问接口
 * @author likang
 * @version 2017-08-11
 */
@MyBatisDao
public interface AccessLogDAO extends BaseDao<AccessLog,String> {

}

/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.baiqishi.entity.ReportGoOutData;

/**
 * 白骑士-出行数据-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface ReportGoOutDataDao extends BaseDao<ReportGoOutData,String> {
	
}
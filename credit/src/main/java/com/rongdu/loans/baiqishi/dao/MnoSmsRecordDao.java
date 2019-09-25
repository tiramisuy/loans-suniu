/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.baiqishi.entity.MnoSmsRecord;

/**
 * 白骑士-运营商短信记录-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface MnoSmsRecordDao extends BaseDao<MnoSmsRecord,String> {
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.CollectionRecord;

/**
 * 催收记录-数据访问接口
 * @author zhangxiaolong
 * @version 2017-10-09
 */
@MyBatisDao
public interface CollectionRecordDao extends BaseDao<CollectionRecord,String> {
	
}
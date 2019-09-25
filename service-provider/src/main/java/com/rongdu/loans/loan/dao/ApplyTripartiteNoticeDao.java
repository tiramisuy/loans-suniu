/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ApplyTripartiteNotice;

/**
 * 工单通知-数据访问接口
 * @author Lee
 * @version 2018-06-01
 */
@MyBatisDao
public interface ApplyTripartiteNoticeDao extends BaseDao<ApplyTripartiteNotice,String> {
	
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.option.CancelLog;

/**
 * loan_cancel_log-数据访问接口
 * @author qf
 * @version 2019-02-26
 */
@MyBatisDao
public interface CancelLogDao extends BaseDao<CancelLog,String> {
	
}
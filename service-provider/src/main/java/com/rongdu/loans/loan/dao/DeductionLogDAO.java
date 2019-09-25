/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.DeductionLog;

import java.util.List;

/**
 * 减免操作日志-数据访问接口
 * @author zhangxiaolong
 * @version 2017-07-27
 */
@MyBatisDao
public interface DeductionLogDAO extends BaseDao<DeductionLog,String> {

    List<DeductionLog> findByItemId(String repayPlanItemId);

    DeductionLog findLastOneByItemId(String repayPlanItemId);

}
/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.OperationLogLater;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 贷款操作日志-数据访问接口
 *
 * @author Lee
 * @version 2018-05-15
 */
@MyBatisDao
public interface OperationLogLaterDao extends BaseDao<OperationLogLater, String> {

    List<OperationLogLater> collectLog(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<OperationLogLater> getCheckLog(String applyId);
}
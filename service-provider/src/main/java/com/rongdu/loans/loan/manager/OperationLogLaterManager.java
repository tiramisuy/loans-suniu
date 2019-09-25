/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.loans.loan.entity.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.OperationLogLater;
import com.rongdu.loans.loan.dao.OperationLogLaterDao;

import java.util.List;

/**
 * 贷款操作日志-实体管理实现类
 * @author Lee
 * @version 2018-05-15
 */
@Service("operationLogLaterManager")
public class OperationLogLaterManager extends BaseManager<OperationLogLaterDao, OperationLogLater, String> {

    @Autowired
    private OperationLogLaterDao operationLogLaterDao;

    /**
     * 查询一段时间内贷款订单审核完成 和 放款 的操作记录
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<OperationLogLater> collectLog(String startTime, String endTime) {
        return operationLogLaterDao.collectLog(startTime, endTime);
    }

    /**
     * 查询贷款订单审核的操作记录
     *
     * @param applyId
     * @return
     */
    public List<OperationLogLater> getCheckLog(String applyId) {
        return operationLogLaterDao.getCheckLog(applyId);
    }
	
}
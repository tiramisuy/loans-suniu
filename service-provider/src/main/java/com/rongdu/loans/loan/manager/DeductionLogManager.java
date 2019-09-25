/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.DeductionLogDAO;
import com.rongdu.loans.loan.entity.DeductionLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 减免操作日志-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-07-27
 */
@Service("deductionLogManager")
public class DeductionLogManager extends BaseManager<DeductionLogDAO, DeductionLog, String> {


    public List<DeductionLog> findByItemId(String repayPlanItemId) {
        return dao.findByItemId(repayPlanItemId);
    }

    /**
     * 查询repayPlanItemId最近的一笔数据
     * @param repayPlanItemId
     * @return
     */
    public DeductionLog findLastOneByItemId(String repayPlanItemId) {
        return dao.findLastOneByItemId(repayPlanItemId);
    }

    /**
     * 最后一笔申请减免的状态
     * @param repayPlanItemId
     */
    public Integer applyCheck(String repayPlanItemId){
        DeductionLog deductionLog= findLastOneByItemId(repayPlanItemId);
        if (deductionLog == null || deductionLog.getStatus() == null){
            return -1;
        }
        return deductionLog.getStatus();
    }
	
}
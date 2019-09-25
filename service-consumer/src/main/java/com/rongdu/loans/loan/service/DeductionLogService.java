/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.loans.loan.option.DeductionApplyOP;
import com.rongdu.loans.loan.option.DeductionApproveOP;
import com.rongdu.loans.loan.vo.DeductionLogVO;


/**
 * 减免操作日志-业务逻辑接口
 * @author zhangxiaolong
 * @version 2017-07-27
 */
public interface DeductionLogService {

    /**
     * 减免申请
     * @param deductionApplyOP
     * @return
     */
    int apply(DeductionApplyOP deductionApplyOP) throws Exception;

    int approve(DeductionApproveOP deductionApproveOP) throws Exception;

    List<DeductionLogVO> deductionFrom(String repayPlanItemId);
    
    /**
     * 最后一笔申请减免的状态
     * @param repayPlanItemId
     */
    Integer applyCheck(String repayPlanItemId);

}
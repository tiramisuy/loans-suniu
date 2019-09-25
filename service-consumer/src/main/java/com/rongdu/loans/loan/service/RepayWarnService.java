/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.RepayWarnOP;
import com.rongdu.loans.loan.vo.RepayWarnVO;

import javax.validation.constraints.NotNull;

/**
 * 还款-预提醒表-业务逻辑接口
 * @author fy
 * @version 2019-07-23
 */
public interface RepayWarnService {
    Page<RepayWarnVO> getRepayWarnList(@NotNull(message = "参数不能为空") RepayWarnOP op);

    void updateWarn(String id, String content);

    /**
     * 还款预提醒分配(当日7点前未还数据)
     * @return
     */
    TaskResult allotRepayWarn();
}
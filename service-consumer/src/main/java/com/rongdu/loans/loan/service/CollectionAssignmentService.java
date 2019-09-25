/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.CollectionAssignmentOP;
import com.rongdu.loans.loan.vo.CollectionAssignmentVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 催收分配记录-业务逻辑接口
 * @author zhangxiaolong
 * @version 2017-09-28
 */
public interface CollectionAssignmentService {

    List<CollectionAssignmentVO> getAllByRepayPlanItemId(@NotNull(message = "参数不能为空") String repayPlanItemId);

    void doAllotment(@NotNull(message = "参数不能为空") CollectionAssignmentOP collectionAssignmentOP);
    /**
     * 催收分配退回任务
     * @return
     */
    TaskResult returnBack();

}
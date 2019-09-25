/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.risk.vo.AutoApproveVO;

/**
 * 自动审批结果-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface AutoApproveService {

	/**
	 * 准备对现金贷贷款申请进行自动审批
	 */
	public void prepareApproveXjd(String applyId,String source);

	/**
	 * 对现金贷贷款申请进行审批
	 * @param applyId
	 * @return
	 */
	public AutoApproveVO approveXjd(String applyId);

	/**
	 * 现金贷预备审批定时任务，查询待审批的数据，放入预备审批的消息队列
	 * @return
	 */
	public TaskResult prepareApproveXjdTask();


	/**
	 * 现金贷自动审批定时任务，查询待审批的数据，放入自动审批的消息队列
	 * @return
	 */
	public TaskResult approveXjdTask();

}
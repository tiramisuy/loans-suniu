package com.rongdu.loans.koudai.service;

import com.rongdu.common.task.TaskResult;
/**
 * 
* @Description:  创建订单
* @author: RaoWenbiao
* @date 2018年9月25日
 */
public interface KDCreateService {
	/**
	 * 
	* @Title: processCreateOrderTask
	* @Description: 创建订单
	* @return    设定文件
	* @return TaskResult    返回类型
	* @throws
	 */
	TaskResult processCreateOrderTask();
}

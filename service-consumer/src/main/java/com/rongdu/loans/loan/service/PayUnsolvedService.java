package com.rongdu.loans.loan.service;

import com.rongdu.common.task.TaskResult;

/**  
 * code y0621
* @Title: PayUnsolvedService.java  
* @Package com.rongdu.loans.loan.service  
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/
public interface PayUnsolvedService {
	
	/**
	* @Title: processPayUnsolvedOrders  
	* @Description: 查询先锋代发处理中订单并更新  
	* @return TaskResult    返回类型  
	 */
	TaskResult processPayUnsolvedOrders();
	
}

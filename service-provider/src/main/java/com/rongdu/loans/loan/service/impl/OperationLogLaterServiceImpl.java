/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.OperationLogLater;
import com.rongdu.loans.loan.service.OperationLogLaterService;
import com.rongdu.loans.loan.manager.OperationLogLaterManager;
/**
 * 贷款操作日志-业务逻辑实现类
 * @author Lee
 * @version 2018-05-15
 */
@Service("operationLogLaterService")
public class OperationLogLaterServiceImpl  extends BaseService implements  OperationLogLaterService{
	
	/**
 	* 贷款操作日志-实体管理接口
 	*/
	@Autowired
	private OperationLogLaterManager operationLogLaterManager;
	
}
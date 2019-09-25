/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportCrossValidationManager;
import com.rongdu.loans.baiqishi.service.ReportCrossValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-交叉信息验证-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportCrossValidationService")
public class ReportCrossValidationServiceImpl  extends BaseService implements  ReportCrossValidationService{
	
	/**
 	* 白骑士-交叉信息验证-实体管理接口
 	*/
	@Autowired
	private ReportCrossValidationManager reportCrossValidationManager;
	
}
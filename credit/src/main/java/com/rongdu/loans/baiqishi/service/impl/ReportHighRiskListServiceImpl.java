/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportHighRiskListManager;
import com.rongdu.loans.baiqishi.service.ReportHighRiskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-高风险名单-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportHighRiskListService")
public class ReportHighRiskListServiceImpl  extends BaseService implements  ReportHighRiskListService{
	
	/**
 	* 白骑士-高风险名单-实体管理接口
 	*/
	@Autowired
	private ReportHighRiskListManager reportHighRiskListManager;
	
}
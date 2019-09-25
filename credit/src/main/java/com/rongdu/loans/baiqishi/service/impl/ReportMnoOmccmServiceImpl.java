/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoOmccmManager;
import com.rongdu.loans.baiqishi.service.ReportMnoOmccmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-常用联系电话（近1个月)-mnoOneMonthCommonlyConnectMobiles-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoOmccmService")
public class ReportMnoOmccmServiceImpl  extends BaseService implements  ReportMnoOmccmService{
	
	/**
 	* 白骑士-常用联系电话（近1个月)-mnoOneMonthCommonlyConnectMobiles-实体管理接口
 	*/
	@Autowired
	private ReportMnoOmccmManager reportMnoOmccmManager;
	
}
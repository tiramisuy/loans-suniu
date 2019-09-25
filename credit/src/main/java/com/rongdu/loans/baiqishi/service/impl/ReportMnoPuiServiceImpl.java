/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoPuiManager;
import com.rongdu.loans.baiqishi.service.ReportMnoPuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商分时间段统计数据-mnoPeriodUsedInfos-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoPuiService")
public class ReportMnoPuiServiceImpl  extends BaseService implements  ReportMnoPuiService{
	
	/**
 	* 白骑士-运营商分时间段统计数据-mnoPeriodUsedInfos-实体管理接口
 	*/
	@Autowired
	private ReportMnoPuiManager reportMnoPuiManager;
	
}
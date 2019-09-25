/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportGoOutDataManager;
import com.rongdu.loans.baiqishi.service.ReportGoOutDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-出行数据-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportGoOutDataService")
public class ReportGoOutDataServiceImpl  extends BaseService implements  ReportGoOutDataService{
	
	/**
 	* 白骑士-出行数据-实体管理接口
 	*/
	@Autowired
	private ReportGoOutDataManager reportGoOutDataManager;
	
}
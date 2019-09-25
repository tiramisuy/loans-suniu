/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoCccaManager;
import com.rongdu.loans.baiqishi.service.ReportMnoCccaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-联系人通话活动地区-mnoContactsCommonlyConnectAreas-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoCccaService")
public class ReportMnoCccaServiceImpl  extends BaseService implements  ReportMnoCccaService{
	
	/**
 	* 白骑士-联系人通话活动地区-mnoContactsCommonlyConnectAreas-实体管理接口
 	*/
	@Autowired
	private ReportMnoCccaManager reportMnoCccaManager;
	
}
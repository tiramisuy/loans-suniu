/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoCcaManager;
import com.rongdu.loans.baiqishi.service.ReportMnoCcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-本人通话活动地区-mnoCommonlyConnectAreas-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoCcaService")
public class ReportMnoCcaServiceImpl  extends BaseService implements  ReportMnoCcaService{
	
	/**
 	* 白骑士-本人通话活动地区-mnoCommonlyConnectAreas-实体管理接口
 	*/
	@Autowired
	private ReportMnoCcaManager reportMnoCcaManager;
	
}
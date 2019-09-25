/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoCcmManager;
import com.rongdu.loans.baiqishi.service.ReportMnoCcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-常用联系电话(近6个月)mnoCommonlyConnectMobiles-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoCcmService")
public class ReportMnoCcmServiceImpl  extends BaseService implements  ReportMnoCcmService{
	
	/**
 	* 白骑士-常用联系电话(近6个月)mnoCommonlyConnectMobiles-实体管理接口
 	*/
	@Autowired
	private ReportMnoCcmManager reportMnoCcmManager;
	
}
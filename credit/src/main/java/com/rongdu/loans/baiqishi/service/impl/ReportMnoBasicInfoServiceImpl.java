/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoBasicInfoManager;
import com.rongdu.loans.baiqishi.service.ReportMnoBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商基本信息-baseInfo-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoBasicInfoService")
public class ReportMnoBasicInfoServiceImpl  extends BaseService implements  ReportMnoBasicInfoService{
	
	/**
 	* 白骑士-运营商基本信息-baseInfo-实体管理接口
 	*/
	@Autowired
	private ReportMnoBasicInfoManager reportMnoBasicInfoManager;
	
}
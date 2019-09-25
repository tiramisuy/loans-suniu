/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportEmergencyContactManager;
import com.rongdu.loans.baiqishi.service.ReportEmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-紧急联系人信息-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportEmergencyContactService")
public class ReportEmergencyContactServiceImpl  extends BaseService implements  ReportEmergencyContactService{
	
	/**
 	* 白骑士-紧急联系人信息-实体管理接口
 	*/
	@Autowired
	private ReportEmergencyContactManager reportEmergencyContactManager;
	
}
/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportCommonlyContactManager;
import com.rongdu.loans.baiqishi.service.ReportCommonlyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-常用联系人（推测）-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportCommonlyContactService")
public class ReportCommonlyContactServiceImpl  extends BaseService implements  ReportCommonlyContactService{
	
	/**
 	* 白骑士-常用联系人（推测）-实体管理接口
 	*/
	@Autowired
	private ReportCommonlyContactManager reportCommonlyContactManager;
	
}
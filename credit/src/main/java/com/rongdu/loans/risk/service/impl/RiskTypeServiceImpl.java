/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.risk.manager.RiskTypeManager;
import com.rongdu.loans.risk.service.RiskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 风险分类-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("riskTypeService")
public class RiskTypeServiceImpl  extends BaseService implements  RiskTypeService{
	
	/**
 	* 风险分类-实体管理接口
 	*/
	@Autowired
	private RiskTypeManager riskTypeManager;
	
}
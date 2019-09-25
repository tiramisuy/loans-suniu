/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.MnoSmsRecordManager;
import com.rongdu.loans.baiqishi.service.MnoSmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商短信记录-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("mnoSmsRecordService")
public class MnoSmsRecordServiceImpl  extends BaseService implements  MnoSmsRecordService{
	
	/**
 	* 白骑士-运营商短信记录-实体管理接口
 	*/
	@Autowired
	private MnoSmsRecordManager mnoSmsRecordManager;
	
}
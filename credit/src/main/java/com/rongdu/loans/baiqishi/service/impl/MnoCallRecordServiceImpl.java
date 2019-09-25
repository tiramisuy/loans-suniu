/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.MnoCallRecordManager;
import com.rongdu.loans.baiqishi.service.MnoCallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商通话记录-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("mnoCallRecordService")
public class MnoCallRecordServiceImpl  extends BaseService implements  MnoCallRecordService{
	
	/**
 	* 白骑士-运营商通话记录-实体管理接口
 	*/
	@Autowired
	private MnoCallRecordManager mnoCallRecordManager;
	
}
/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.MnoBillRecordManager;
import com.rongdu.loans.baiqishi.service.MnoBillRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商账单记录-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("mnoBillRecordService")
public class MnoBillRecordServiceImpl  extends BaseService implements  MnoBillRecordService{
	
	/**
 	* 白骑士-运营商账单记录-实体管理接口
 	*/
	@Autowired
	private MnoBillRecordManager mnoBillRecordManager;
	
}
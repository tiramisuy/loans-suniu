/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.credit.entity.ApiInvokeLog;
import com.rongdu.loans.credit.manager.ApiInvokeLogManager;
import com.rongdu.loans.credit.service.ApiInvokeLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 征信数据合作机构接口调用日志-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("apiInvokeLogService")
public class ApiInvokeLogServiceImpl  extends BaseService implements  ApiInvokeLogService{
	
	/**
 	* 征信数据合作机构接口调用日志-实体管理接口
 	*/
	@Autowired
	private ApiInvokeLogManager apiInvokeLogManager;
	
	@Transactional
	public int insert(ApiInvokeLog entity){
		return apiInvokeLogManager.insert(entity);
	}
	
	@Transactional
	public int insertBatch(List<ApiInvokeLog> list){
		return apiInvokeLogManager.insertBatch(list);
	}
	
	
}
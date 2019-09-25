/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.service;

import com.rongdu.loans.credit.entity.ApiInvokeLog;

import java.util.List;

/**
 * 征信数据合作机构接口调用日志-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface ApiInvokeLogService {
		
	/**
	 * 插入征信数据合作机构接口调用日志
	 * @param entity
	 * @return
	 */
	public int insert(ApiInvokeLog entity);
	
	/**
	 * 批量插入征信数据合作机构接口调用日志
	 * @param list
	 * @return
	 */
	public int insertBatch(List<ApiInvokeLog> list);
	
}
/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.tongdun.manager.AntifraudApiManager;
import com.rongdu.loans.tongdun.service.AntifraudApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 同盾-反欺诈决策服务-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("antifraudApiService")
public class AntifraudApiServiceImpl  extends BaseService implements  AntifraudApiService{
	
	/**
 	* 同盾-反欺诈决策服务-实体管理接口
 	*/
	@Autowired
	private AntifraudApiManager antifraudApiManager;
	
}
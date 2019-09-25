/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.zhicheng.manager.EchoApiManager;
import com.rongdu.loans.zhicheng.service.EchoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("echoApiService")
public class EchoApiServiceImpl  extends BaseService implements  EchoApiService{
	
	/**
 	* 致诚信用-阿福共享平台-查询借款、风险名单和逾期信息-实体管理接口
 	*/
	@Autowired
	private EchoApiManager echoApiManager;
	
}
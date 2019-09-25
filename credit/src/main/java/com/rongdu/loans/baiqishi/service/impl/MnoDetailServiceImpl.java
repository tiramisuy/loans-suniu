/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.MnoDetailManager;
import com.rongdu.loans.baiqishi.service.MnoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商数据-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("mnoDetailService")
public class MnoDetailServiceImpl  extends BaseService implements  MnoDetailService{
	
	/**
 	* 白骑士-运营商数据-实体管理接口
 	*/
	@Autowired
	private MnoDetailManager mnoDetailManager;
	
}
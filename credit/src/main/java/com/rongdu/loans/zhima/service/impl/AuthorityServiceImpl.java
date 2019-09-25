/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhima.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.zhima.manager.AuthorityManager;
import com.rongdu.loans.zhima.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 芝麻信用授权-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("authorityService")
public class AuthorityServiceImpl  extends BaseService implements  AuthorityService{
	
	/**
 	* 芝麻信用授权-实体管理接口
 	*/
	@Autowired
	private AuthorityManager authorityManager;
	
}
/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongdun.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.tongdun.manager.AntifraudPolicyManager;
import com.rongdu.loans.tongdun.service.AntifraudPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 同盾-反欺诈策略-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("antifraudPolicyService")
public class AntifraudPolicyServiceImpl  extends BaseService implements  AntifraudPolicyService{
	
	/**
 	* 同盾-反欺诈策略-实体管理接口
 	*/
	@Autowired
	private AntifraudPolicyManager antifraudPolicyManager;
	
}
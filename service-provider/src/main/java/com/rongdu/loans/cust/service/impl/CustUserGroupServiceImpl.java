/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.cust.entity.CustUserGroup;
import com.rongdu.loans.cust.service.CustUserGroupService;
import com.rongdu.loans.cust.manager.CustUserGroupManager;

/**
 * 用户分组表-业务逻辑实现类
 * 
 * @author liuzhuang
 * @version 2018-04-17
 */
@Service("custUserGroupService")
public class CustUserGroupServiceImpl extends BaseService implements CustUserGroupService {

	/**
	 * 用户分组表-实体管理接口
	 */
	@Autowired
	private CustUserGroupManager custUserGroupManager;

	@Override
	public int insert(String storeId, String groupId, String userId) {
		CustUserGroup entity = new CustUserGroup();
		entity.setStoreId(storeId);
		entity.setGroupId(groupId);
		entity.setUserId(userId);
		custUserGroupManager.insert(entity);
		return 0;
	}
}
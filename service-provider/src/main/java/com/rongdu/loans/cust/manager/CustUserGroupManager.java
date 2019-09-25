/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.cust.entity.CustUserGroup;
import com.rongdu.loans.cust.dao.CustUserGroupDao;

/**
 * 用户分组表-实体管理实现类
 * @author liuzhuang
 * @version 2018-04-18
 */
@Service("custUserGroupManager")
public class CustUserGroupManager extends BaseManager<CustUserGroupDao, CustUserGroup, String> {
	
}
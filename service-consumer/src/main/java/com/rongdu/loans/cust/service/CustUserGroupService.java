/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.service;

/**
 * 用户分组表-业务逻辑接口
 * 
 * @author liuzhuang
 * @version 2018-04-17
 */
public interface CustUserGroupService {
	int insert(String storeId, String groupId, String userId);
}
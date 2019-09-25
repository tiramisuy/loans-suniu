/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.credit.manager.ContactListManager;
import com.rongdu.loans.credit.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 用户通讯录-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("contactListService")
public class ContactListServiceImpl  extends BaseService implements  ContactListService{
	
	/**
 	* 用户通讯录-实体管理接口
 	*/
	@Autowired
	private ContactListManager contactListManager;
	
}
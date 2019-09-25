/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.credit.dao.ContactListDao;
import com.rongdu.loans.credit.entity.ContactList;
import org.springframework.stereotype.Service;

/**
 * 用户通讯录-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("contactListManager")
public class ContactListManager extends BaseManager<ContactListDao, ContactList, String>{
	
}
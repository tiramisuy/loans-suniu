/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.loans.loan.dao.UserMd5Dao;
import com.rongdu.loans.loan.entity.UserMd5;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;


/**
 * 用户md5-实体管理实现类
 * @author Lee
 * @version 2018-07-09
 */
@Service("userMd5Manager")
public class UserMd5Manager extends BaseManager<UserMd5Dao, UserMd5, String> {
	
}
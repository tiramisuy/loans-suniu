/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-18 15:29:48
 *
 *******************************************************************************/
package com.rongdu.loans.dao.account;

import org.springframework.stereotype.Repository;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.account.VUser;

/**
 * VUser 数据访问类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Repository
public class VUserDao extends HibernateDao<VUser, Long> {
	
}

/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-18 20:04:11
 *
 *******************************************************************************/
package com.rongdu.loans.service.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.dao.account.VRoleDao;
import com.rongdu.loans.entity.account.VRole;
import com.rongdu.loans.service.EntityManager;
/**
 * VRole业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class VRoleManager extends EntityManager<VRole, Long>{
	
	@Autowired
	private VRoleDao vRoleDao;

	public void setVRoleDao(VRoleDao vRoleDao) {
		this.vRoleDao = vRoleDao;
	}

	@Override
	protected HibernateDao<VRole, Long> getEntityDao() {
		return vRoleDao;
	}
	
}

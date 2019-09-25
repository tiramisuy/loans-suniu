/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-10 22:34:58
 *
 *******************************************************************************/
package com.rongdu.loans.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.dao.account.VUserRoleDao;
import com.rongdu.loans.entity.account.VUserRole;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.service.log.Log;
import com.rongdu.loans.service.log.LogService;


@Service
@Transactional
@LogService(obj="用户")
public class VUserRoleManager extends EntityManager<VUserRole, Long>{
	
	@Autowired
	private VUserRoleDao vUserRoleDao;

	@Override
	protected HibernateDao<VUserRole, Long> getEntityDao() {
		return vUserRoleDao;
	}

	public VUserRole findUniqueBy(Long roleId,Long userId) {
		return vUserRoleDao.findUniqueBy(roleId, userId);
	}
	
	@Override
	@Log(opt="移除角色")
	public void delete(VUserRole entity)  {
		vUserRoleDao.delete(entity.getRoleId(), entity.getUserId());
	}
	@Log(opt="批量移除角色")
	public int batchDelete(Long roleId, Long[] userIds) {
		return vUserRoleDao.delete(roleId, userIds);
	}
	
}

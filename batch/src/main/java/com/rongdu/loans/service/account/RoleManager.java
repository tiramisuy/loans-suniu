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
import com.rongdu.core.security.springsecurity.SpringSecuritys;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.account.RoleDao;
import com.rongdu.loans.entity.account.Role;
import com.rongdu.loans.exception.ServiceException;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.service.log.Log;
import com.rongdu.loans.service.log.LogService;
import com.rongdu.loans.utils.StringUtil;
/**
 * 系统资源业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
@LogService(obj="角色")
public class RoleManager extends EntityManager<Role, Long>{
	
	@Autowired
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	protected HibernateDao<Role, Long> getEntityDao() {
		return roleDao;
	}
	
	public int batchDelete(Long[] ids) {
		return roleDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(Role.class, propertyName));
		Long count = roleDao.countByProperty(propertyName,value);
		return count==0L;
	}
	
	@Override
	@Log(opt="删除")
	public void delete(Role entity) {
		if (isAdminRole(entity.getId())) {
			logger.warn("操作员{}尝试删除超级管理员角色", SpringSecuritys.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		};
		roleDao.delete(entity);
	}
	@Log(obj="授权管理",opt="修改")
	public void updateRole(Role entity){
		
		roleDao.update(entity);
	}
	private boolean isAdminRole(Long id) {
		return 1L==id;
	}
	
}

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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.security.springsecurity.SpringSecuritys;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.account.UserDao;
import com.rongdu.loans.entity.account.User;
import com.rongdu.loans.exception.ServiceException;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.service.log.Log;
import com.rongdu.loans.service.log.LogService;
import com.rongdu.loans.utils.SecurityUtil;
import com.rongdu.loans.utils.StringUtil;
/**
 *用户业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
@LogService(obj="用户信息")
public class UserManager extends EntityManager<User, Long>{
	
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	protected HibernateDao<User, Long> getEntityDao() {
		return userDao;
	}
	
	public int batchDelete(Long[] ids) {
		return userDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(User.class, propertyName));
		Long count = userDao.countByProperty(propertyName,value);
		return count==0L;
	}
	
	@Override
	@Log(opt="新增")
	public void save(User entity) {
		String pswd = SecurityUtil.shaPassword(entity.getPassword(), entity.getSalt());
		entity.setPassword(pswd);			
		userDao.save(entity);
	}
	
	@Override
	@Log(opt="修改")
	public void update(User entity) {
		userDao.update(entity);
	}
	@Log(opt="修改密码")
	public void changePassword(User entity) {
		String pswd = SecurityUtil.shaPassword(entity.getPassword(), entity.getSalt());
		entity.setPassword(pswd);		
		userDao.update(entity);
	}
	
	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	@Override
	@Log(opt="删除用户")
	public void delete(User entity) {
		if (isSupervisor(entity.getId())) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecuritys.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(entity);
	}
	
	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
	
	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	/**
	 * 检查用户名是否唯一.
	 *
	 * @return username在数据库中唯一或等于oldUsername时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isUsernameUnique(String newUsername, String oldUsername) {
		return userDao.isPropertyUnique("username", newUsername, oldUsername);
	}
	@Log(opt="移出黑名单")
	public int enabledUser(Long id) {
		String status = "1";
		return userDao.batchUpdateUserStatus(status,id);
	}
	@Log(opt="加入黑名单")
	public int disabledUser(Long id) {
		String status = "0";
		return userDao.batchUpdateUserStatus(status,id);
	}
	@Log(opt="批量移出黑名单")
	public int batchEnabledUser(Long[] ids) {
		String status = "1";
		return userDao.batchUpdateUserStatus(status,ids);
	}
	@Log(opt="批量加入黑名单")
	public int batchDisabledUser(Long[] ids) {
		String status = "0";
		return userDao.batchUpdateUserStatus(status,ids);
	}
	
	public long[] findUserIdByRole(Long roleId) {
		return userDao.findUserIdByRole(roleId);
	}
	
	public Page<User> findPage(Page<User> page,String hql,Map<String, String> values) {
		return userDao.findPage(page, hql, values);
	}

	public void batchRegister(List<User> list) {
		for (User user:list) {
			userDao.save(user);
		}
	}
	
}

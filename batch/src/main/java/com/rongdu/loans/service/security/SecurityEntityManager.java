package com.rongdu.loans.service.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.security.springsecurity.SpringSecuritys;
import com.rongdu.loans.dao.account.AuthorityDao;
import com.rongdu.loans.dao.account.RoleDao;
import com.rongdu.loans.dao.account.UserDao;
import com.rongdu.loans.entity.account.Authority;
import com.rongdu.loans.entity.account.Role;
import com.rongdu.loans.entity.account.User;
import com.rongdu.loans.exception.ServiceException;


/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class SecurityEntityManager {

	private static Logger logger = LoggerFactory.getLogger(SecurityEntityManager.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;

	//-- User Manager --//
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		userDao.save(entity);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户",
					SpringSecuritys.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public User loadUserByUsername(String loginName) {
		return userDao.findUniqueBy("username", loginName);
	}

	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("username", newLoginName, oldLoginName);
	}

	//-- Role Manager --//
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		return userDao.getAll("id", true);
	}
	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll("id", true);
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}
}

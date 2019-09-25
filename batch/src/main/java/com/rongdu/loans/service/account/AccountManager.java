package com.rongdu.loans.service.account;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import com.rongdu.loans.utils.SecurityUtil;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	private UserDao userDao;
	private RoleDao roleDao;
	private AuthorityDao authorityDao;

	//-- User Manager --//
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		if (entity.getId()==null) {
			String hashPass = SecurityUtil.shaPassword(entity.getPassword(), entity.getSalt());
			entity.setPassword(hashPass);			
		}
		userDao.saveOrUpdate(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecuritys.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}
	
	@Transactional(readOnly = true)
	public List<User> searchUser(String propertyName, Object value) {
		return userDao.findBy(propertyName, value);
	}
	
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		return userDao.getAll();
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

	//-- Role Manager --//
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}
	
	/**
	 * 使用属性过滤条件查询角色.
	 */
	@Transactional(readOnly = true)
	public Page<Role> searchRole(final Page<Role> page, final List<PropertyFilter> filters) {
		return roleDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll("id", true);
	}

	public void saveRole(Role entity) {
		roleDao.saveOrUpdate(entity);
	}

	public void deleteRole(Long id) {
		if (isAdminRole(id)) {
			logger.warn("操作员{}尝试删除超级管理员角色", SpringSecuritys.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		};
		roleDao.delete(id);
	}

	private boolean isAdminRole(Long id) {
		return 1L==id;
	}

	//-- Authority Manager --//
	public Authority getAuthority(Long id) {
		return authorityDao.get(id);
	}

	public Page<Authority> searchAuthority(Page<Authority> page,
			List<PropertyFilter> filters) {
		return authorityDao.findPage(page, filters);
	}
	
	public List<Authority> searchAuthority(List<PropertyFilter> filters) {
		return authorityDao.find(filters);
	}

	public void saveAuthority(Authority entity) {
		authorityDao.saveOrUpdate(entity);		
	}
	
	public void deleteAuthority(Long id) {
		authorityDao.delete(id);		
	}
	
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}
	
	public int batchDeleteAuthority(Long[] ids) {
		return authorityDao.batchDelete(ids);
	}	
	
	public int batchEnabledUser(Long[] ids) {
		String status = "1";
		return userDao.batchUpdateUserStatus(status,ids);
	}
	
	public int batchDisabledUser(Long[] ids) {
		String status = "0";
		return userDao.batchUpdateUserStatus(status,ids);
	}
	
	public int batchDelete(Long[] ids) {
		return userDao.batchDelete(ids);
	}
	
	public long[] findUserIdByRole(Long roleId) {
		return userDao.findUserIdByRole(roleId);
	}
	
	public Page<User> findPage(Page<User> page,String hql,Map<String, String> values) {
		return userDao.findPage(page, hql, values);
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
}

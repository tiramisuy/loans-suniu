package com.rongdu.loans.dao.account;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.account.Role;
import com.rongdu.loans.entity.account.User;

/**
 * 角色对象的泛型DAO.
 */
@Component
public class RoleDao extends HibernateDao<Role, Long> {

	private static final String QUERY_USER_BY_ROLEID = "select u from User u left join u.roleList r where r.id=?";

	/**
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Role role = get(id);
		//查询出拥有该角色的用户,并删除该用户的角色.
		List<User> users = createQuery(QUERY_USER_BY_ROLEID, role.getId()).list();
		for (User u : users) {
			u.getRoleList().remove(role);
		}
		super.delete(role);
	}
	
	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from Role role where role.id in ");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from Role where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
}

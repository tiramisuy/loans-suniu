package com.rongdu.loans.dao.account;


import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.account.VUserRole;

@Component
public class VUserRoleDao extends HibernateDao<VUserRole, Long> {

	public VUserRole findUniqueBy(Long roleId, Long userId) {
		String hql = "from VUserRole where roleId=? and userId=?";
		return findUnique(hql, roleId,userId);
	}
	
	public void delete(Long roleId, Long userId) {
		String hql = "delete from SS_USER_ROLE where role_id=:roleId and user_id=:userId";
		SQLQuery query =  getSession().createSQLQuery(hql);
		query.setParameter("roleId", roleId);
		query.setParameter("userId", userId);
		query.executeUpdate();
	}

	public int delete(Long roleId, Long[] userIds) {
		String userId = StringUtils.join(userIds, ',');
		StringBuilder builder = new StringBuilder("delete from SS_USER_ROLE where role_id=");
		builder.append(roleId);
		builder.append(" and user_id in(");
		builder.append(userId);
		builder.append(")");
		String hql = builder.toString();
		SQLQuery query =  getSession().createSQLQuery(hql);
		return query.executeUpdate();
	}

}

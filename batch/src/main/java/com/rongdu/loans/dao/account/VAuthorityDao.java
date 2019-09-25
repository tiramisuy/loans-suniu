package com.rongdu.loans.dao.account;


import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.account.VAuthority;

/**
 * 授权对象的泛型DAO.
 */
@Component
public class VAuthorityDao extends HibernateDao<VAuthority, Long> {

	public VAuthority getSimpleVAuthority(Long id) {
		String hql = "SELECT new VAuthority(id,name)  FROM VAuthority where id=?";
		return findUnique(hql,id);
	}
	
}

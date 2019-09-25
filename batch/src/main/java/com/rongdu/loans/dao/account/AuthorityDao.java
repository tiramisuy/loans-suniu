package com.rongdu.loans.dao.account;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.constant.AuthorityType;
import com.rongdu.loans.entity.account.Authority;

/**
 * 授权对象的泛型DAO.
 * 
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {
	
	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from Authority authority where authority.id in ");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from Authority where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}

	public List<Authority> loadOperation(Long id) {
		String hql = "from Authority where type=? and pid=? order by rank asc";
		return find(hql,AuthorityType.OPT,id);
	}

	public void deleteOperation(Long id) {
		String hql = "delete from Authority where pid=?";
		batchExecute(hql, id);
	}

	public List<Authority> getAllMenu() {
		String hql = "from Authority where type=? order by rank asc";
		return find(hql,AuthorityType.MENU);
	}
	
	public List<Authority> getAllOpt() {
		String hql = "from Authority where type=? order by code asc";
		return find(hql,AuthorityType.OPT);
	}
	
}

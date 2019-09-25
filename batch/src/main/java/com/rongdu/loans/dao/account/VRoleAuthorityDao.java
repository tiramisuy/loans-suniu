/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-19 16:51:26
 *
 *******************************************************************************/
package com.rongdu.loans.dao.account;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.constant.AuthorityType;
import com.rongdu.loans.entity.account.VRoleAuthority;

/**
 * VRoleAuthority 数据访问类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Repository
public class VRoleAuthorityDao extends HibernateDao<VRoleAuthority, Long> {
	
	@Transactional(readOnly=true)
	@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
	public List<VRoleAuthority> loadMenuAuthorityByRole(List<Long> ids) {
		String params = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("select new VRoleAuthority(a.authId, a.pid,a.name,a.url) from VRoleAuthority a where a.roleId=(select max(b.roleId) from VRoleAuthority b where a.authId=b.authId and a.roleId=b.roleId) and a.roleId in ");
		hql.append("(").append(params).append(") and a.type=? order by a.pid asc,a.rank asc");
		return find(hql.toString(),AuthorityType.MENU);
	}

	public List<String> loadOperationAuthorityByRole(List<Long> ids) {
		String params = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("select a.code from VRoleAuthority a where a.roleId=(select max(b.roleId) from VRoleAuthority b where a.authId=b.authId and a.roleId=b.roleId) and a.roleId in ");
		hql.append("(").append(params).append(") and a.type=?");
		return find(hql.toString(),AuthorityType.OPT);
	}

}

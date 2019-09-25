/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:15:47
 *
 *******************************************************************************/
package com.rongdu.loans.dao.account;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.account.Dept;

/**
 * Dept 数据访问类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Repository
public class DeptDao extends HibernateDao<Dept, Long> {
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from Dept where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
	
	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from Dept entity where entity.id in");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
}

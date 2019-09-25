/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-14 16:08:07
 *
 *******************************************************************************/
package com.rongdu.loans.dao.batch;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.batch.ScheduleTask;

/**
 * 定时任务 数据访问类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Repository
public class ScheduleTaskDao extends HibernateDao<ScheduleTask, String> {
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from ScheduleTask where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
	
	public int batchDelete(String[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from ScheduleTask entity where entity.id in");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
}

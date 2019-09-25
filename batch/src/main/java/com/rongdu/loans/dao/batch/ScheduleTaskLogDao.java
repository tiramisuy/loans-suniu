/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-14 16:07:47
 *
 *******************************************************************************/
package com.rongdu.loans.dao.batch;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.batch.ScheduleTaskLog;

/**
 * 定时任务执行日志 数据访问类
 *
 * @version 1.0
 *
 * @author sunda
 */
@Repository
public class ScheduleTaskLogDao extends HibernateDao<ScheduleTaskLog, String> {
	
	public Long countByProperty(String propertyName, Object value) {
		String hql = "from ScheduleTaskLog where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}
	
	public int batchDelete(String[] ids) {
		String lp = StringUtils.join(ids, ',');	
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from ScheduleTaskLog entity where entity.id in");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	
}

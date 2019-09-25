/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 13:21:12
 *
 *******************************************************************************/
package com.rongdu.loans.service.log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.log.AccessLogDao;
import com.rongdu.loans.entity.log.AccessLog;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.utils.StringUtil;
/**
 * 访问日志业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class AccessLogManager extends EntityManager<AccessLog, Long>{
	
	@Autowired
	private AccessLogDao accessLogDao;

	public void setAccessLogDao(AccessLogDao accessLogDao) {
		this.accessLogDao = accessLogDao;
	}

	@Override
	protected HibernateDao<AccessLog, Long> getEntityDao() {
		return accessLogDao;
	}
	
	public int batchDelete(Long[] ids) {
		return accessLogDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(AccessLog.class, propertyName));
		Long count = accessLogDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
}

/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-14 16:07:48
 *
 *******************************************************************************/
package com.rongdu.loans.service.batch;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.batch.ScheduleTaskLogDao;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.entity.batch.ScheduleTaskLog;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.utils.StringUtil;
/**
 * 定时任务执行日志业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class ScheduleTaskLogManager extends EntityManager<ScheduleTaskLog, String>{
	
	@Autowired
	private ScheduleTaskLogDao scheduleTaskLogDao;

	public void setScheduleTaskLogDao(ScheduleTaskLogDao scheduleTaskLogDao) {
		this.scheduleTaskLogDao = scheduleTaskLogDao;
	}

	@Override
	protected HibernateDao<ScheduleTaskLog, String> getEntityDao() {
		return scheduleTaskLogDao;
	}
	
	@Transactional
	public int batchDelete(String[] ids) {
		return scheduleTaskLogDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(ScheduleTaskLog.class, propertyName));
		Long count = scheduleTaskLogDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
	
	@Override
	@Transactional
	public void save(ScheduleTaskLog entity){
		VUser user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication!=null) {
			user = (VUser)authentication.getPrincipal();
		}
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		if (user!=null) {
			entity.setCreateBy(String.valueOf(user.getId()));
			entity.setUpdateBy(String.valueOf(user.getId()));
		}else {
			entity.setCreateBy("system");
			entity.setUpdateBy("system");
		}
		scheduleTaskLogDao.save(entity);
	}
	
	@Override
	@Transactional
	public void update(ScheduleTaskLog entity){
		VUser user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication!=null) {
			user = (VUser)authentication.getPrincipal();
		}
		entity.setUpdateTime(new Date());
		if (user!=null) {
			entity.setUpdateBy(String.valueOf(user.getId()));
		}else {
			entity.setUpdateBy("system");
		}
		scheduleTaskLogDao.update(entity);
	}
	
}

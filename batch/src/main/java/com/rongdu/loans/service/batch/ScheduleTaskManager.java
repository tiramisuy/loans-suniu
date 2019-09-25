/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-14 16:08:07
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
import com.rongdu.loans.common.AbstractJobFactory;
import com.rongdu.loans.dao.batch.ScheduleTaskDao;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.entity.batch.ScheduleTask;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.utils.StringUtil;
/**
 * 定时任务业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class ScheduleTaskManager extends EntityManager<ScheduleTask, String>{
	
	@Autowired
	private ScheduleTaskDao scheduleTaskDao;

	public void setScheduleTaskDao(ScheduleTaskDao scheduleTaskDao) {
		this.scheduleTaskDao = scheduleTaskDao;
	}
	
	@Override
	protected HibernateDao<ScheduleTask, String> getEntityDao() {
		return scheduleTaskDao;
	}
	
	@Transactional
	public int batchDelete(String[] ids) {
		return scheduleTaskDao.batchDelete(ids);
	}
	
	@Override
	@Transactional
	public void save(ScheduleTask entity){
		VUser user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication!=null) {
			user = (VUser)authentication.getPrincipal();
		}
		entity.setCreateTime(new Date());
		entity.setStatus(AbstractJobFactory.TASK_STATUS_DEFAULT);
		entity.setUpdateTime(new Date());
		if (user!=null) {
			entity.setCreateBy(String.valueOf(user.getId()));
			entity.setUpdateBy(String.valueOf(user.getId()));
		}else {
			entity.setCreateBy("system");
			entity.setUpdateBy("system");
		}
		scheduleTaskDao.save(entity);
	}
	
	@Override
	@Transactional
	public void update(ScheduleTask entity){
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
		scheduleTaskDao.update(entity);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(ScheduleTask.class, propertyName));
		Long count = scheduleTaskDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
}

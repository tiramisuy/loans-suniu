/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-10-7 20:53:38
 *
 *******************************************************************************/
package com.rongdu.loans.service.log;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.log.LoginLogDao;
import com.rongdu.loans.dao.log.VLoginLogDao;
import com.rongdu.loans.entity.log.LoginLog;
import com.rongdu.loans.entity.log.VLoginLog;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.utils.StringUtil;
/**
 * 登录日志业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
@LogService(obj="登录日志")
public class LoginLogManager extends EntityManager<LoginLog, Long>{
	
	@Autowired
	private LoginLogDao loginLogDao;
	@Autowired
	private VLoginLogDao vLoginLogDao;

	@Override
	protected HibernateDao<LoginLog, Long> getEntityDao() {
		return loginLogDao;
	}
	
	@Log(obj="批量删除")
	public int batchDelete(Long[] ids) {
		return loginLogDao.batchDelete(ids);
	}
	
	public void  insert(LoginLog entity) {
		getEntityDao().save(entity);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(LoginLog.class, propertyName));
		Long count = loginLogDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
//	@Log(opt="查询登录日志列表",obj="后台登录日志")
	public Page<VLoginLog> findVLoginLogByPage(Page<VLoginLog> page,List<PropertyFilter> filters) {
		return vLoginLogDao.findPage(page, filters);
	}

	public List<VLoginLog> findVLoginLog(List<PropertyFilter> filters) {
		return vLoginLogDao.find(filters);
	}
	
	public List<VLoginLog> findVLoginLogOrderby(String hql) {
		return vLoginLogDao.find(hql);
	}
	
	public VLoginLog getVLoginLog(Long id) {
		return vLoginLogDao.get(id);
	}
	
}

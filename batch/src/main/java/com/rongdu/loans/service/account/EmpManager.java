/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:15:10
 *
 *******************************************************************************/
package com.rongdu.loans.service.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.account.EmpDao;
import com.rongdu.loans.entity.account.Emp;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.utils.StringUtil;
/**
 * Emp业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class EmpManager extends EntityManager<Emp, Long>{
	
	@Autowired
	private EmpDao sysEmpDao;

	public void setEmpDao(EmpDao sysEmpDao) {
		this.sysEmpDao = sysEmpDao;
	}

	@Override
	protected HibernateDao<Emp, Long> getEntityDao() {
		return sysEmpDao;
	}
	
	public int batchDelete(Long[] ids) {
		return sysEmpDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(Emp.class, propertyName));
		Long count = sysEmpDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
}

/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:15:47
 *
 *******************************************************************************/
package com.rongdu.loans.service.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.account.DeptDao;
import com.rongdu.loans.entity.account.Dept;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.utils.StringUtil;
/**
 * Dept业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class DeptManager extends EntityManager<Dept, Long>{
	
	@Autowired
	private DeptDao sysDeptDao;

	public void setDeptDao(DeptDao sysDeptDao) {
		this.sysDeptDao = sysDeptDao;
	}

	@Override
	protected HibernateDao<Dept, Long> getEntityDao() {
		return sysDeptDao;
	}
	
	public int batchDelete(Long[] ids) {
		return sysDeptDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(Dept.class, propertyName));
		Long count = sysDeptDao.countByProperty(propertyName,value);
		if (count==0L) {
			return true;
		}
		return false;
	}
	
}

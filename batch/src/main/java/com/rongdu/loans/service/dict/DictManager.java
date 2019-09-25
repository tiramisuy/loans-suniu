package com.rongdu.loans.service.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.dao.dict.DictDao;
import com.rongdu.loans.entity.dict.Dict;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.service.log.LogService;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
@LogService(obj="数据字典")
public class DictManager extends EntityManager<Dict, String>{

	private DictDao dictDao;
	
	@Autowired
	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}
	
	public boolean isPropertyValueExist(String property, Object value) {
		return dictDao.isPropertyValueExist(property,value);
	}

	@Override
	protected HibernateDao<Dict, String> getEntityDao() {
		return dictDao;
	}
}

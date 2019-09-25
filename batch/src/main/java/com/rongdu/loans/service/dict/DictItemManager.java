package com.rongdu.loans.service.dict;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.dao.dict.DictItemDao;
import com.rongdu.loans.entity.dict.DictItem;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.service.log.LogService;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
@LogService(obj="字典明细")
public class DictItemManager extends EntityManager<DictItem, Long> {

	private DictItemDao dictItemDao;

	@Autowired
	public void setDictItemDao(DictItemDao dictItemDao) {
		this.dictItemDao = dictItemDao;
	}

	@Override
	protected HibernateDao<DictItem, Long> getEntityDao() {
		return dictItemDao;
	}
	
	public Map<String, String> findDictItemMap(String dictCode){
		List<DictItem> list = dictItemDao.findByDictCode(dictCode);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (!list.isEmpty()) {
			for (DictItem d:list) {
				map.put(d.getCode(), d.getName());
			}		
		}
		return map;
	}
	
	public boolean isPropertyValueExist(String code, String codeValue,String dictCode, String dictCodeValue) {
		return dictItemDao.isPropertyValueExist(code,codeValue,dictCode,dictCodeValue);
	}
	
	public Page<DictItem> findPage(Page<DictItem> page,String hql,Object...values){
		return getEntityDao().findPage(page, hql, values);
	}
	
	public Page<DictItem> findPage(Page<DictItem> page,String hql,Map<String, Object> map){
		return getEntityDao().findPage(page, hql, map);
	}
}
package com.rongdu.loans.dao.dict;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.dict.DictItem;

/**
 * 字典项对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class DictItemDao extends HibernateDao<DictItem, Long> {
	
	public List<DictItem> findByDictCode(String dictCode){
		String hql = "select new DictItem(e.code,e.name) from DictItem e where e.dict.code=? order by e.code";
		return find(hql, dictCode);
	}
	
	public boolean isPropertyValueExist(String code, String codeValue, String dictCode, String dictCodeValue) {
		String hql = "from DictItem where "+code+"=?"+" and "+dictCode+"=?";
		long count = countHqlResult(hql,codeValue,dictCodeValue);
		if (count>0) {
			return true;
		}
		return false;
	}

}

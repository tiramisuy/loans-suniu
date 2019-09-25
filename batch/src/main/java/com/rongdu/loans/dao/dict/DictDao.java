package com.rongdu.loans.dao.dict;

import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.dict.Dict;


/**
 * 字典对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class DictDao extends HibernateDao<Dict, String> {

	public boolean isPropertyValueExist(String propertyName, Object value) {
		Dict dict = findUniqueBy(propertyName, value);
		if (dict!=null) {
			return true;
		}
		return false;
	}

}

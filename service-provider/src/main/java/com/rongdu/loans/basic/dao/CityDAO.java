/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import java.util.List;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.City;

/**
 * 市-数据访问接口
 * @author wy
 * @version 2017-10-23
 */
@MyBatisDao
public interface CityDAO extends BaseDao<City,String> {
	
	/**
	 * 获取所有市
	 * @return
	 */
	List<City> getAll();
	
	/**
	 * 根据省代码获取市
	 * @param pid
	 * @return
	 */
	List<City> getByProvinceId(Integer pid);

	String getById(Integer id);
}
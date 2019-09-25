/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.Area;
import com.rongdu.loans.basic.entity.Province;
import com.rongdu.loans.basic.vo.AreaSimpleVO;

import java.util.List;

/**
 * 省-数据访问接口
 * @author wy
 * @version 2017-10-23
 */
@MyBatisDao
public interface ProvinceDAO extends BaseDao<Province,String> {
	
	/**
	 * 获取所有省
	 * @return
	 */
	List<Province> getAll();
	
	/**
	 * 根据省代码获取省名称
	 * @param id
	 * @return
	 */
	String getProvince(Integer id);
}
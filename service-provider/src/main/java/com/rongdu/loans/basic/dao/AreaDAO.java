/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.Area;
import com.rongdu.loans.basic.vo.AreaSimpleVO;

import java.util.List;

/**
 * 地区-数据访问接口
 * @author sunda
 * @version 2017-08-23
 */
@MyBatisDao
public interface AreaDAO extends BaseDao<Area,String> {
	
	/**
	 * 获取地区代码和地区名称
	 * @return
	 */
	List<AreaSimpleVO> getAreaCodeAndName();
	
	/**
	 * 根据地区代码获取地区名称
	 * @param id
	 * @return
	 */
	String getAreaName(String id);
}
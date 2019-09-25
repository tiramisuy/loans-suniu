/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.dao;

import java.util.List;

import com.rongdu.common.persistence.TreeDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.sys.entity.Area;
import com.rongdu.loans.sys.entity.Location;

/**
 * 区域DAO接口
 * @author sunda
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
	/*
	 * 根据用户id查询定位信息
	 */
	List<Location> getLocationInfo(String userId);
	
	List<Area> getAllArea();
	
}

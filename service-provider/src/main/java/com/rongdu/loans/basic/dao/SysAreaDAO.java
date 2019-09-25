/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import java.util.List;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.Store;

/**
 * 区域-数据访问接口
 * @author fy
 * @version 2018-01-09
 */
@MyBatisDao
public interface SysAreaDAO extends BaseDao<Store,String> {
	
	/**
	 * 获取所有区域
	 * @return
	 */
	List<Store> getAllArea();
	
}
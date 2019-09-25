/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.Store;

/**
 * 门店-数据访问接口
 * 
 * @author fy
 * @version 2018-01-09
 */
@MyBatisDao
public interface StoreDAO extends BaseDao<Store, String> {

	/**
	 * 获取区域下所有门店
	 * 
	 * @return
	 */
	List<Store> getAllStore(@Param("areaId")String areaId,@Param("productId")String productId);

	/**
	 * 获取门店下所有组
	 * 
	 * @param storeId
	 * @return
	 */
	List<Store> getAllGroup(String storeId);

	Store getBycompayId(String companyId);

	List<Store> getAllTopCompany();

	List<Store> getStoreByAreaAndCompany(@Param("areaId")String areaId,@Param("companyId")String companyId);
}
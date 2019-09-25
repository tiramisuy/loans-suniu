/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service;

import java.util.List;

import com.rongdu.loans.basic.vo.StoreVO;

/**
 * 门店-业务逻辑接口
 * 
 * @author fy
 * @version 2018-01-09
 */
public interface StoreService {

	/**
	 * 获得区域下所有门店
	 * 
	 * @return
	 */
	public List<StoreVO> getAllStore(String areaId,String productId);

	/**
	 * 获取门店下所有组
	 * 
	 * @param storeId
	 * @return
	 */
	public List<StoreVO> getAllGroup(String storeId);

	/**
	 * 获得所有区域
	 * 
	 * @return
	 */
	public List<StoreVO> getAllArea();

	StoreVO getBycompayId(String companyId);
	
	/**
	 * 获取所有一级公司
	 * 
	 * @return
	 */
	List<StoreVO> getAllTopCompany();
	
	/**
	 * 获得区域下指定公司所有门店
	 * 
	 * @return
	 */
	public List<StoreVO> getStoreByAreaAndCompany(String areaId,String companyId);
}
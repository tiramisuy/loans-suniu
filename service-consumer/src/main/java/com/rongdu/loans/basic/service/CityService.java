/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service;

import java.util.List;

import com.rongdu.loans.basic.vo.CityVO;

/**
 * 市-业务逻辑接口
 * 
 * @author wy
 * @version 2017-10-23
 */
public interface CityService {

	/**
	 * 获得省下所有得属市
	 * 
	 * @return
	 */
	public List<CityVO> getAllCityByPid(Integer pid);

	public List<CityVO> getAll();

	/*
	 * 根据id获取名称
	 */
	String getById(int id);
	
	Integer getIdByName(String name);
}
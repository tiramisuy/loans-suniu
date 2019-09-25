/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.basic.dao.CityDAO;
import com.rongdu.loans.basic.entity.City;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.vo.CityVO;

/**
 * 地区-业务逻辑实现类
 * 
 * @author sunda
 * @version 2017-08-23
 */
@Service("cityService")
public class CityServiceImpl extends BaseService implements CityService {
	public static final String CITY_LIST_CACHE_KEY = "CITY_LIST";
	public static final String CITY_LIST_CACHE_PREFIX = "CITY_LIST_";
	/**
	 * 市-实体管理接口
	 */
	@Autowired
	private CityDAO cityDao;

	@Override
	public List<CityVO> getAllCityByPid(Integer pid) {
		String cacheKey = CITY_LIST_CACHE_PREFIX + pid;
		List<City> list = (List<City>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			list = cityDao.getByProvinceId(pid);
			JedisUtils.setObject(cacheKey, list, Global.ONE_DAY_CACHESECONDS);
		}
		return BeanMapper.mapList(list, CityVO.class);
	}

	@Override
	public List<CityVO> getAll() {
		String cacheKey = CITY_LIST_CACHE_KEY;
		List<City> list = (List<City>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			list = cityDao.getAll();
			JedisUtils.setObject(cacheKey, list, Global.ONE_DAY_CACHESECONDS);
		}
		return BeanMapper.mapList(list, CityVO.class);
	}

	@Override
	public String getById(int id) {
		List<CityVO> list = getAll();
		String name = null;
		if (list == null) {
			name = cityDao.getById(id);
		} else {
			for (CityVO city : list) {
				if (city.getId() == id) {
					name = city.getName();
					break;
				}
			}
		}
		return name;
	}

	@Override
	public Integer getIdByName(String name) {
		List<CityVO> list = getAll();
		Integer id = null;
		if (list != null) {
			for (CityVO city : list) {
				if (city.getName().equals(name)) {
					id = city.getId();
					break;
				}
			}
		}
		return id;
	}
}
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
import com.rongdu.loans.basic.dao.ProvinceDAO;
import com.rongdu.loans.basic.entity.Province;
import com.rongdu.loans.basic.service.ProvinceService;
import com.rongdu.loans.basic.vo.ProvinceVO;

/**
 * 地区-业务逻辑实现类
 * 
 * @author wy
 * @version 2017-10-23
 */
@Service("provinceService")
public class ProvinceServiceImpl extends BaseService implements ProvinceService {
	public static final String PROVINCE_LIST_CACHE_KEY = "PROVINCE_LIST";
	/**
	 * 市-实体管理接口
	 */
	@Autowired
	private ProvinceDAO provinceDAO;

	@Override
	public List<ProvinceVO> getAllList() {
		String cacheKey = PROVINCE_LIST_CACHE_KEY;
		List<Province> list = (List<Province>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			list = provinceDAO.getAll();
			JedisUtils.setObject(cacheKey, list, Global.ONE_DAY_CACHESECONDS);
		}
		return BeanMapper.mapList(list, ProvinceVO.class);
	}

}
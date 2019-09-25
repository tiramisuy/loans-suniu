/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.xinyan.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.xinyan.entity.RadarDetail;
import com.rongdu.loans.xinyan.service.RadarDetailService;
import com.rongdu.loans.xinyan.manager.RadarDetailManager;

/**
 * 新颜全景雷达明细-业务逻辑实现类
 * 
 * @author liuzhuang
 * @version 2018-11-19
 */
@Service("radarDetailService")
public class RadarDetailServiceImpl extends BaseService implements RadarDetailService {

	/**
	 * 新颜全景雷达明细-实体管理接口
	 */
	@Autowired
	private RadarDetailManager radarDetailManager;

	@Override
	public int save(RadarDetail detail) {
		return radarDetailManager.insert(detail);
	}

}
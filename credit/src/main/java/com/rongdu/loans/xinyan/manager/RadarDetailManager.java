/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.xinyan.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.xinyan.entity.RadarDetail;
import com.rongdu.loans.xinyan.dao.RadarDetailDao;

/**
 * 新颜全景雷达明细-实体管理实现类
 * @author liuzhuang
 * @version 2018-11-19
 */
@Service("radarDetailManager")
public class RadarDetailManager extends BaseManager<RadarDetailDao, RadarDetail, String> {
	
}
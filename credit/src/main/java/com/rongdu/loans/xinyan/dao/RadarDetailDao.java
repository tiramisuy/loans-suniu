/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.xinyan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.xinyan.entity.RadarDetail;

/**
 * 新颜全景雷达明细-数据访问接口
 * @author liuzhuang
 * @version 2018-11-19
 */
@MyBatisDao
public interface RadarDetailDao extends BaseDao<RadarDetail,String> {
	
}
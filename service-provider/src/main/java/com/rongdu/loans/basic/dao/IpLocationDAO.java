/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.IpLocation;

/**
 * 基于IP的地理定位信息-数据访问接口
 * @author likang
 * @version 2017-08-15
 */
@MyBatisDao
public interface IpLocationDAO extends BaseDao<IpLocation,String> {

	/**
	 * 根据ip统计数据条数
	 * @param ip
	 * @return
	 */
	int countByIp(String ip);
	
	/**
	 * 根据ip查询地理位置信息
	 * @param ip
	 * @return
	 */
	IpLocation getByIp(String ip);
}

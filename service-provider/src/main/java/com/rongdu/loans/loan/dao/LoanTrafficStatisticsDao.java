/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanTrafficStatistics;

/**
 * 贷超导流统计-数据访问接口
 * @author raowb
 * @version 2018-08-29
 */
@MyBatisDao
public interface LoanTrafficStatisticsDao extends BaseDao<LoanTrafficStatistics, String> {
	/**
	 * 
	* @Title: addHits
	* @Description: 统计数+1
	* @param trafficId
	* @param statsDate
	* @return    设定文件
	* @return int    返回类型
	* @throws
	 */
	public int addHits(@Param("trafficId") String trafficId, @Param("statsDate") Integer statsDate);
}
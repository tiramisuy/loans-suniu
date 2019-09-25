/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.MarketAllot;
import com.rongdu.loans.loan.vo.MarketAllotVO;

/**
 * 营销客户分配-数据访问接口
 * @author liul
 * @version 2018-10-15
 */
@MyBatisDao
public interface MarketAllotDao extends BaseDao<MarketAllot,String> {
	public MarketAllotVO getMarketByCustUserId(@Param(value = "userId")String userId);
	
	public int updateMarket(@Param(value = "op")MarketAllotVO op);
	
	public MarketAllotVO getAllotMarketByid(@Param(value = "allotId")String allotId);
	
	public MarketAllotVO getAllotMarketByCustUserId(@Param(value = "userId")String userId);
}
/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.MarketAllot;
import com.rongdu.loans.loan.vo.MarketAllotVO;
import com.rongdu.loans.loan.dao.MarketAllotDao;

/**
 * 营销客户分配-实体管理实现类
 * @author liul
 * @version 2018-10-15
 */
@Service("marketAllotManager")
public class MarketAllotManager extends BaseManager<MarketAllotDao, MarketAllot, String> {
	public MarketAllotVO getMarketByCustUserId(String userId){
		return dao.getMarketByCustUserId(userId);
	}
	
	public int updateMarket(MarketAllotVO op){
		return dao.updateMarket(op);
	}
	
	public MarketAllotVO getAllotMarketByid(String allotId){
		return dao.getAllotMarketByid(allotId);
	}
	
	public MarketAllotVO getAllotMarketByCustUserId(String userId){
		return dao.getAllotMarketByCustUserId(userId);
	}
}
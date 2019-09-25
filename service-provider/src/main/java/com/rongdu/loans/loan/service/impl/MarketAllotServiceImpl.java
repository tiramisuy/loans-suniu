/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.MarketAllot;
import com.rongdu.loans.loan.manager.MarketAllotManager;
import com.rongdu.loans.loan.option.MarketAllotUserOP;
import com.rongdu.loans.loan.service.MarketAllotService;
import com.rongdu.loans.loan.vo.MarketAllotVO;
/**
 * 营销客户分配-业务逻辑实现类
 * @author liul
 * @version 2018-10-15
 */
@Service("marketAllotService")
public class MarketAllotServiceImpl  extends BaseService implements  MarketAllotService{
	
	/**
 	* 营销客户分配-实体管理接口
 	*/
	@Autowired
	private MarketAllotManager marketAllotManager;
	
	@Override
	public MarketAllotVO getMarketByCustUserId(String userId){
		return marketAllotManager.getMarketByCustUserId(userId);
	}
	
	@Override
	public int allotInsert(String updateBy,List<String> ids,List<MarketAllotUserOP> userList){
		int success = 0;
		int a = 0;
		int total = userList.size();
		if(total>0){
				List<MarketAllot> marketList = new ArrayList<MarketAllot>();
				if(!CollectionUtils.isEmpty(ids)){
					for(String id : ids){
						MarketAllotUserOP userOp = 	userList.get(a % total);
						
						MarketAllot market = new MarketAllot();
							market.setCustUserId(id);
							market.setUserId(userOp.getUserId());
							market.setUserName(userOp.getUserName());
							market.setCallTime(0);
							market.setUpdateBy(updateBy);
							market.setAllotDate(new Date());
						marketList.add(market);
						a++;
						
					}
				}
				if (!CollectionUtils.isEmpty(marketList)) {
					success = marketAllotManager.insertBatch(marketList);
				}
		}
		return success;
	}
	
	@Override
	public int updateMarket(MarketAllotVO op){
		return marketAllotManager.updateMarket(op);
	}
	
	@Override
	public MarketAllotVO getAllotMarketByid(String allotId){
		return marketAllotManager.getAllotMarketByid(allotId);
	}
	
	@Override
	public MarketAllotVO getAllotMarketByCustUserId(String userId){
		return marketAllotManager.getAllotMarketByCustUserId(userId);
	}
}
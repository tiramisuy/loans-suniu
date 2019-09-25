/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.loans.loan.option.MarketAllotUserOP;
import com.rongdu.loans.loan.vo.MarketAllotVO;

/**
 * 营销客户分配-业务逻辑接口
 * @author liul
 * @version 2018-10-15
 */
public interface MarketAllotService {
	
	public MarketAllotVO getMarketByCustUserId(String userId);
	
	public int allotInsert(String updateBy,List<String> ids,List<MarketAllotUserOP> userList);
	
	public int updateMarket(MarketAllotVO op);
	
	public MarketAllotVO getAllotMarketByid(String allotId);
	
	public MarketAllotVO getAllotMarketByCustUserId(String userId);
}
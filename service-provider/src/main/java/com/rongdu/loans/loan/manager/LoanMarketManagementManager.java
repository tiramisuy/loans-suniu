/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;


import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.LoanMarketManagementDao;
import com.rongdu.loans.loan.entity.LoanMarketManagement;
import com.rongdu.loans.loan.option.LoanMarketCountOP;
import com.rongdu.loans.loan.option.LoanMarketManagementOP;
import com.rongdu.loans.loan.vo.LoanMarketCountVO;
import com.rongdu.loans.loan.vo.LoanMarketManagementVO;
import com.rongdu.loans.loan.vo.MarketManagementVO;

/**
 * 营销分配管理
 * @author liuliang
 * @version 2018-05-22
 */
@Service("loanMarketManagementManager")
public class LoanMarketManagementManager extends BaseManager<LoanMarketManagementDao, LoanMarketManagement, String> {
	
	public int insertMarket(LoanMarketManagement op){
		return dao.insertMarket(op);
	}
	
	public List<LoanMarketManagementVO> getMarketList(Page page,LoanMarketManagementOP op){
		return dao.getMarketList(page,op);
	}
	
	
	public List<LoanMarketCountVO> getMarketCountList(LoanMarketCountOP op){
		return dao.getMarketCountList(op);
	}
	
	public MarketManagementVO getMarketById(String id){
		return dao.getMarketById(id);
	}
	
	public int updateMarket(MarketManagementVO market){
		return dao.updateMarket(market);
	}
	
	public MarketManagementVO getMarketByApplyId(String applyId){
		return dao.getMarketByApplyId(applyId);
	}
}
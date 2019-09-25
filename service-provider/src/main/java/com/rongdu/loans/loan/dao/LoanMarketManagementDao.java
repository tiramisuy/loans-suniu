/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanMarketManagement;
import com.rongdu.loans.loan.option.LoanMarketCountOP;
import com.rongdu.loans.loan.option.LoanMarketManagementOP;
import com.rongdu.loans.loan.vo.LoanMarketCountVO;
import com.rongdu.loans.loan.vo.LoanMarketManagementVO;
import com.rongdu.loans.loan.vo.MarketManagementVO;

/**
 * 还款提醒-数据访问接口
 * @author liuliang
 * @version 2018-05-22
 */
@MyBatisDao
public interface LoanMarketManagementDao extends BaseDao<LoanMarketManagement,String> {
	public int insertMarket(LoanMarketManagement op);
	
	public List<LoanMarketManagementVO> getMarketList(@Param(value = "page") Page<LoanMarketManagementVO>  page,@Param(value = "op") LoanMarketManagementOP op);

	public List<LoanMarketCountVO> getMarketCountList(@Param(value = "op") LoanMarketCountOP op);
	
	public int updateMarket(@Param(value = "op")MarketManagementVO op);
	
	public MarketManagementVO getMarketById(@Param(value = "id")String id);
	
	public MarketManagementVO getMarketByApplyId(@Param(value = "applyId")String id);
}
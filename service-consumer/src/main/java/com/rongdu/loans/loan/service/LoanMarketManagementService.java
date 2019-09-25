package com.rongdu.loans.loan.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.LoanMarketCountOP;
import com.rongdu.loans.loan.option.LoanMarketManagementOP;
import com.rongdu.loans.loan.option.MarketAllotUserOP;
import com.rongdu.loans.loan.vo.LoanMarketCountVO;
import com.rongdu.loans.loan.vo.LoanMarketManagementVO;
import com.rongdu.loans.loan.vo.MarketManagementVO;

public interface LoanMarketManagementService {

	/**
	 * 营销分配
	 * @return
	 */
	public TaskResult batchInsertMarketManagemetn();
	
	public Page<LoanMarketManagementVO> getMarketList(@NotNull(message = "分页参数不能为空") Page page,
			@NotNull(message = "参数不能为空")LoanMarketManagementOP op);
	
	
	public List<LoanMarketCountVO> getMarketCountList(@NotNull(message = "参数不能为空")LoanMarketCountOP op);
	
	
	MarketManagementVO getMarketById(String id);
	
	int updateMarket(MarketManagementVO repa);
	
	public MarketManagementVO getMarketByApplyId(String applyId);
	
	public int allotInsert(String updateBy,List<String> ids,List<MarketAllotUserOP> userList);
	
	public List<LoanMarketManagementVO> findMarketList(LoanMarketManagementOP op);
}

package com.rongdu.loans.loan.service;

import java.util.List;
import java.util.Map;

import com.rongdu.loans.loan.vo.LoanPromotionCaseVO;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.vo.CostingResultVO;

/**
 * 营销方案服务接口
 * @author likang
 *
 */
public interface PromotionCaseService {

	/**
	 * 费用计算
	 * @return
	 */
	CostingResultVO Costing(PromotionCaseOP promotionCaseOP);
	
	/**
	 * 查看是否特定渠道
	 * @param channel
	 * @return
	 */
	String checkByChannel(String channel);
	
	public List<Map<String, String>> findAllChannel();

	LoanPromotionCaseVO getById(String id);
}

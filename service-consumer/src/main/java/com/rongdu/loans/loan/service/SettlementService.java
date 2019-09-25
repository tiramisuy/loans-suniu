package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.StatementVO;

/**
 * 还款结算服务接口
 * 
 * @author likang
 * 
 */
public interface SettlementService {

	/**
	 * 根据还款明细id查询还款计划详情
	 * 
	 * @param repayPlanItemId
	 * @return
	 */
	StatementVO getStatementByItemId(String repayPlanItemId);

	/**
	 * 还款结算
	 * 
	 * @param rePayOP
	 * @return
	 */
	ConfirmPayResultVO settlement(RePayOP rePayOP);

	/**
	 * 延期
	 * 
	 * @param rePayOP
	 * @return
	 */
	ConfirmPayResultVO delay(RePayOP rePayOP);

	/**
	 * 加急
	 * 
	 * @param rePayOP
	 * @return
	 */
	ConfirmPayResultVO urgent(RePayOP rePayOP);

	/**
	 * 加急（推标放款）
	 * @param rePayOP
	 * @return
	 */
	ConfirmPayResultVO urgentPushingLoan(RePayOP rePayOP);

	/**
	 * 旅游券
	 * 
	 * @param rePayOP
	 * @return
	 */
	ConfirmPayResultVO trip(RePayOP rePayOP);
	
	/**
	 * 是否可进行购物券抵扣
	 * @param repayPlanItemId
	 * @return
	 */
	boolean isEnableCouponDeduction(String repayPlanItemId);
	
}

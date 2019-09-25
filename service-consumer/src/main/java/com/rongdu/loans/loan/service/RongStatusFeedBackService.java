package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.rong360Model.*;

/**  
* @Title: RongFeedBackService.java  
* @Package com.rongdu.loans.loan.service  
* @Description: 融360用户订单 状态反馈 
* @author: yuanxianchu  
* @date 2018年7月4日  
* @version V1.0  
*/
public interface RongStatusFeedBackService {
	
	/**
	* @Title: rongCancelStatusFeedBack  
	* @Description: 贷款取消-订单状态反馈
	 */
	boolean rongCancelStatusFeedBack(String applyId) throws Exception;
	
	/**
	* @Title: rongApproveStatusFeedBack  
	* @Description: 审批结论反馈
	 */
	boolean rongApproveStatusFeedBack(String applyId);

	/**
	* @Title: rongLendStatusFeedBack  
	* @Description: 放款-订单状态反馈
	 */
	boolean rongLendStatusFeedBack(String applyId);
	
	/**
	* @Title: rongDelayStatusFeedBack  
	* @Description: 贷款延期-还款或展期结果反馈
	 */
	boolean rongDelayStatusFeedBack(String repayPlanItemId,String applyId,boolean result);
	
	/**
	* @Title: rongOverdueStatusFeedBack  
	* @Description: 贷款逾期-订单状态反馈
	 */
	boolean rongOverdueStatusFeedBack(String orderNo);
	
	/**
	* @Title: rongSettlementStatusFeedBack  
	* @Description: 贷款结清-订单状态反馈
	 */
	boolean rongSettlementStatusFeedBack(String repayPlanItemId,String applyId,boolean result,Integer repayType);
	
	/**
	* @Title: rongRepayStatusFeedBack  
	* @Description: 还款-还款或展期结果反馈
	 */
	boolean rongRepayStatusFeedBack(String repayPlanItemId,String applyId, boolean result, Integer repayType);
	
	/**
	 * 暂未启用
	* @Title: rongContractStatusFeedBack  
	* @Description: 合同状态反馈
	 */
	void rongContractStatusFeedBack(String applyId) throws Exception;
	
	/**
	* @Title: rongBindCardStatusFeedBack  
	* @Description: 绑卡结果反馈
	 */
	void rongBindCardStatusFeedBack(BindCardOP bindCardOP,Rong360Resp rong360Resp) throws Exception;
	
	/**
	* @Title: pullApproveStatusFeedBack  
	* @Description: 拉取审批状态  
	 */
	RongApproveFeedBackOP pullApproveStatusFeedBack(String orderNo);
	
	/**
	* @Title: pullOrderStatusFeedBack  
	* @Description: 拉取订单状态  
	 */
	RongOrderFeedBackOP pullOrderStatusFeedBack(String orderNo);

    /**
     * 拉取还款计划
     */
    RongPushRepaymentOP pullRepaymentPlan(String orderNo);

    /**
     * 推送订单状态
     */
    Rong360FeedBackResp rongPushOrderStatus(String orderNo) throws Exception;
	
}

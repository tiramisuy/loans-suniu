package com.rongdu.loans.loan.service;
/**  
* @Title: DWDStatusFeedBackService.java  
* @Package com.rongdu.loans.loan.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/

import com.rongdu.loans.common.ThirdApiDTO;
import com.rongdu.loans.loan.option.dwd.BankVerifyOP;
import com.rongdu.loans.loan.option.dwd.DWDResp;
import com.rongdu.loans.loan.vo.dwd.AuditResultVO;
import com.rongdu.loans.loan.vo.dwd.OrderStatusVO;
import com.rongdu.loans.loan.vo.dwd.PaymentStatusVO;
import com.rongdu.loans.loan.vo.dwd.RepaymentPlanVO;

public interface DWDStatusFeedBackService {
	
	/**
	 * 拉取订单状态
	 */
	OrderStatusVO pullOrderStatus(String orderNo);
	
	/**
	 * 拉取审批结论
	 */
	AuditResultVO pullAudiResult(String orderNo);
	
	/**
	 * 拉取还款状态
	 */
	PaymentStatusVO pullPaymentStatus(String orderNo, String repayPlanItemId);
	
	/**
	 * 拉取还款计划
	 */
	RepaymentPlanVO pullRepaymentPlan(String orderNo);
	
	/**
	 * 绑卡验证反馈
	 */
	DWDResp bankVerifyFeedBack(BankVerifyOP bankVerifyOP, DWDResp dwdResp) throws Exception;
	
	/**
	 * 审批结论反馈
	 */
	boolean approveFeedBack(String applyId);
	
	boolean lendFeedBack(String applyId);
	
	boolean overdueFeedBack(String applyId);
	
	boolean settlementFeedBack(String applyId,String repayPlanItemId);

	boolean orderStatusFeedback(String orderNo, boolean flag) throws Exception;

	<T> T requestHandler(ThirdApiDTO thirdApiDTO, Class<T> responseVO) throws Exception;
	
}

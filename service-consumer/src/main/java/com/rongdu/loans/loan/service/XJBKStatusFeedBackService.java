package com.rongdu.loans.loan.service;
/**  
* @Title: XJBKStatusFeedBackService.java  
* @Package com.rongdu.loans.loan.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年8月20日  
* @version V1.0  
*/
public interface XJBKStatusFeedBackService {
	
	/**
	* @Title: xjbkApproveFeedBack  
	* @Description: 现金白卡-审批结果反馈
	 */
	void xjbkApproveFeedBack(String applyId);
	
	/**
	* @Title: xjbkAuthFeedback  
	* @Description: 现金白卡-H5认证结果结果反馈
	 */
	void xjbkAuthFeedback(String applyId, boolean result);
	
	/**
	* @Title: xjbkLendingFeedBack  
	* @Description: 现金白卡-放款结果反馈
	 */
	void xjbkLendingFeedBack(String applyId, boolean result);
	
	/**
	* @Title: rongCancelStatusFeedBack  
	* @Description: 现金白卡-还款结果反馈
	 */
	void xjbkRepayStatusFeedBack(String repayPlanItemId,String applyId, boolean result);
	
	/**
	* @Title: xjbkOverdueStatusFeedBack  
	* @Description: 现金白卡-订单逾期结果反馈
	 */
	void xjbkOverdueStatusFeedBack(String orderNo);
}

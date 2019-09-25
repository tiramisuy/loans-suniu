package com.rongdu.loans.loan.service;

import org.springframework.scheduling.annotation.Async;

import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaRepaymentPlan;

/**  
* @Title: XJBKPushFeedBackService.java  
* @Package com.rongdu.loans.loan.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年8月20日  
* @version V1.0  
*/
public interface XJBKPushFeedBackService {
	
	/**
	* @Title: pushRepayPlan  
	* @Description: 异步推送还款计划 
	* @param @param orderNo
	 */
	@Async
	void pushRepayPlan(String orderNo);
	
	XianJinBaiKaRepaymentPlan pullRepaymentPlan(String orderNo);
}

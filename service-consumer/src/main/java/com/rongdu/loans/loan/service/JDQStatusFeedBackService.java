package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.jdq.JDQOrderStatusFeedBackVO;

/**  
* @Title: JDQStatusFeedBackService.java  
* @Package com.rongdu.loans.loan.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月12日  
* @version V1.0  
*/
public interface JDQStatusFeedBackService {
	
	boolean orderStatusFeedBack(String applyId);
	
	JDQOrderStatusFeedBackVO pullOrderStatus(String orderId);

}

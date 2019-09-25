package com.rongdu.loans.pay.service;
/**  
* @Title: XianFengWithdrawService.java  
* @Package com.rongdu.loans.pay.service  
* @Description: 先锋代付
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/

import com.rongdu.loans.pay.op.XfHandelWithdrawOP;
import com.rongdu.loans.pay.op.XfWithdrawOP;
import com.rongdu.loans.pay.vo.XfWithdrawResultVO;

public interface XianFengWithdrawService {
	
	/**
	* @Title: xfWithdraw  
	* @Description: 先锋单笔代付
	* @param @param op 入参对象
	* @return XfWithdrawResultVO    返回类型  
	 */
	XfWithdrawResultVO xfWithdraw(XfWithdrawOP op);
	
	/**
	* @Title: xfWithdrawQuery  
	* @Description: 先锋单笔代付查询 
	* @param @param merchantNo 商户订单号
	* 		（发起代付时生成，获得响应后作为payLog的ID保存）
	* @return XfWithdrawResultVO    返回类型  
	 */
	XfWithdrawResultVO xfWithdrawQuery(String merchantNo);
	
	
	/**
	 * 先锋手动代付
	 * @param op
	 * @return
	 */
	public XfWithdrawResultVO xfHandekWithDraw(XfHandelWithdrawOP op);
}

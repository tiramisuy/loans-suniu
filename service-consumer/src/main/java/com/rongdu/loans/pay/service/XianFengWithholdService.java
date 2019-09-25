package com.rongdu.loans.pay.service;

import com.rongdu.loans.pay.op.XfWithholdOP;
import com.rongdu.loans.pay.vo.XfWithholdResultVO;

/**
 * code y0602
* @Title: XianFengWithholdService.java  
* @Description: 先锋代扣接口 
* @author: yuanxianchu 
* @date 2018年6月2日  
* @version V1.0
 */
public interface XianFengWithholdService {
	
	/**
	 * 
	* @Title: xfWithholdServFee  
	* @Description: 先锋单笔代扣 
	* @param @param op	入参对象
	* @param @param payType
	* @return XfWithholdResultVO    返回类型  
	 */
	 XfWithholdResultVO xfWithhold(XfWithholdOP op,Integer payType);
	 
	 /**
	  * 
	 * @Title: xfWithholdServFeeQuery  
	 * @Description: 先锋单笔订单查询
	 * @param @param merchantNo 商户订单号
	 * 		（发起代扣时生成，获得响应后作为repayLog的ID保存）
	 * @return XfWithholdResultVO    返回类型  
	  */
	 XfWithholdResultVO xfWithholdQuery(String merchantNo);
}

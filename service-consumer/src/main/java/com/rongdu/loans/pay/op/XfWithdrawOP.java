package com.rongdu.loans.pay.op;

import java.io.Serializable;

import lombok.Data;

/**  
* @Title: XfWithdrawOP.java  
* @Package com.rongdu.loans.pay.op  
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/
@Data
public class XfWithdrawOP implements Serializable{

	private static final long serialVersionUID = -2572635926569974948L;
	
	/**
	 * 金额(单位元)
	 */
	private String amount = "0.00";
	/**
	 * 支付分类
	 */
	private Integer payType;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 订单号
	 */
	private String applyId;
	/**
	 * 合同号
	 */
	private String contractId;
	
}

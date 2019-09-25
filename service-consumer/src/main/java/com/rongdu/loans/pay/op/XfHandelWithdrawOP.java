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
public class XfHandelWithdrawOP implements Serializable{

	private static final long serialVersionUID = -2572635926569974948L;
	
	/**
	 * 金额(单位元)
	 */
	private String amount = "0.00";
	/**
	 * 姓名
	 */
	private String  realName;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	
	/**
	 * 银行编码
	 */
	private String bankCode;
	
}

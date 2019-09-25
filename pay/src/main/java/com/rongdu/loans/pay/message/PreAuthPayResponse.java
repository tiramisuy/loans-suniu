package com.rongdu.loans.pay.message;


/**
 * 预支付-应答消息
 * @author sunda
 * @version 2017-07-10
 */
public class PreAuthPayResponse extends BaofooResponse{
	
	private static final long serialVersionUID = -5243755512234183664L;
	/**
	 * 宝付业务流水号
	 * 用于在后续类交易中唯一标识一笔交易
	 */
	private String business_no;
	
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}


}

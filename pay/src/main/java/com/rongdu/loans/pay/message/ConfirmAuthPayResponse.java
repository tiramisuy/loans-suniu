package com.rongdu.loans.pay.message;


/**
 * 确认支付-应答消息
 * @author sunda
 * @version 2017-07-10
 */
public class ConfirmAuthPayResponse extends BaofooResponse{
	
	private static final long serialVersionUID = -5243755512234183664L;
	/**
	 * 宝付业务流水号
	 * 用于在后续类交易中唯一标识一笔交易
	 */
	private String business_no;
	/**
	 * 成功金额
	 * 交易成功后返回的金额
	 */
	private String succ_amt;
	
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}
	public String getSucc_amt() {
		return succ_amt;
	}
	public void setSucc_amt(String succ_amt) {
		this.succ_amt = succ_amt;
	}

}

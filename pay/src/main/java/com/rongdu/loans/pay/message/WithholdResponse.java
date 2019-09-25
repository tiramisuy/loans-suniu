package com.rongdu.loans.pay.message;


/**
 * 直接绑卡-应答消息
 * @author sunda
 * @version 2017-07-10
 */
public class WithholdResponse extends BaofooResponse{
	
	/**
	 * 宝付交易号
	 */
	private String trans_no;
	/**
	 * 成功金额
	 */
	private String succ_amt;
	/**
	 * 商户订单号
	 *唯一订单号，8-20 位字母和数字，不可重复
	 */
	private String trans_id;

	public String getTrans_no() {
		return trans_no;
	}

	public void setTrans_no(String trans_no) {
		this.trans_no = trans_no;
	}

	public String getSucc_amt() {
		return succ_amt;
	}

	public void setSucc_amt(String succ_amt) {
		this.succ_amt = succ_amt;
	}

	@Override
	public String getTrans_id() {
		return trans_id;
	}

	@Override
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
}

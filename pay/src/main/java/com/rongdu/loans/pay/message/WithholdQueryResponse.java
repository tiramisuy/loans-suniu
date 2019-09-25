package com.rongdu.loans.pay.message;


/**
 * 直接绑卡-应答消息
 * @author sunda
 * @version 2017-07-10
 */
public class WithholdQueryResponse extends BaofooResponse{
	
	/**
	 * 宝付交易号
	 */
	private String trans_no;
	/**
	 * 成功金额
	 */
	private String succ_amt;
	/**
	 * 原交易订单日期
	 */
	private String orig_trade_date;
	/**
	 * 原交易商户订单号
	 */
	private String orig_trans_id;
	/**
	 * 商户流水号
	 */
	private String trans_serial_no;
	/**
	 * 交易结果状态码
	 */
	private String order_stat;

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

	public String getOrig_trade_date() {
		return orig_trade_date;
	}

	public void setOrig_trade_date(String orig_trade_date) {
		this.orig_trade_date = orig_trade_date;
	}

	public String getOrig_trans_id() {
		return orig_trans_id;
	}

	public void setOrig_trans_id(String orig_trans_id) {
		this.orig_trans_id = orig_trans_id;
	}

	@Override
	public String getTrans_serial_no() {
		return trans_serial_no;
	}

	@Override
	public void setTrans_serial_no(String trans_serial_no) {
		this.trans_serial_no = trans_serial_no;
	}

	public String getOrder_stat() {
		return order_stat;
	}

	public void setOrder_stat(String order_stat) {
		this.order_stat = order_stat;
	}
}

package com.rongdu.loans.pay.message;

/**
 * 代扣-加密数据
 * @author zhangxiaolong
 * @version 2017-08-08
 */
public class WithholdQueryDataContent extends BaseDataContent{
	
	private static final long serialVersionUID = -1102657274087235200L;

	/**
	 * 交易子类
	 */
	private String txn_sub_type = "31";
	/**
	 * 原始商户订单号
	 */
	private String orig_trans_id;
	/**
	 * 订单日期
	 */
	private String orig_trade_date;



	@Override
	public String getTxn_sub_type() {
		return txn_sub_type;
	}

	@Override
	public void setTxn_sub_type(String txn_sub_type) {
		this.txn_sub_type = txn_sub_type;
	}

	public String getOrig_trans_id() {
		return orig_trans_id;
	}

	public void setOrig_trans_id(String orig_trans_id) {
		this.orig_trans_id = orig_trans_id;
	}

	public String getOrig_trade_date() {
		return orig_trade_date;
	}

	public void setOrig_trade_date(String orig_trade_date) {
		this.orig_trade_date = orig_trade_date;
	}
}
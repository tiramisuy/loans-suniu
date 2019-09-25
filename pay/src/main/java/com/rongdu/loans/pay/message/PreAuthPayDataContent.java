package com.rongdu.loans.pay.message;

import java.util.Map;

/**
 * 预支付-加密数据
 * @author sunda
 * @version 2017-07-10
 */
public class PreAuthPayDataContent extends BaseDataContent{
	
	private static final long serialVersionUID = 5764135980244786792L;
	/**
	 * 绑定标识号
	 *	用于绑定关系的唯一标识
	 */
	private String bind_id;
	/**
	 * 交易金额
	 *	单位：分，例：1元则提交100
	 */
	private String txn_amt;
	/**
	 * 风险控制参数
	 */
	private  Map<String,String> risk_content;
	
	public String getBind_id() {
		return bind_id;
	}
	public void setBind_id(String bind_id) {
		this.bind_id = bind_id;
	}
	public String getTxn_amt() {
		return txn_amt;
	}
	public void setTxn_amt(String txn_amt) {
		this.txn_amt = txn_amt;
	}
	public Map<String, String> getRisk_content() {
		return risk_content;
	}
	public void setRisk_content(Map<String, String> risk_content) {
		this.risk_content = risk_content;
	}


	
	
}
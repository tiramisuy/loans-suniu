package com.rongdu.loans.pay.message;

import com.rongdu.loans.pay.utils.BaofooConfig;

import java.io.Serializable;

/**
 * 直接绑卡-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class BaofooRequest implements Serializable{

	private static final long serialVersionUID = 7450487966564228776L;
	
	/**
	 * 版本号
	 */
	private String version = "4.0.0.0";
	/**
	 * 终端号
	 */
	private String terminal_id = BaofooConfig.authpay_terminal_id;
	/**
	 * 交易类型
	 */
	private String txn_type = "0431";
	/**
	 * 交易子类
	 */
	private String txn_sub_type ;
	/**
	 * 宝付提供给商户的唯一编号
	 */
	private String member_id = BaofooConfig.member_id;
	/**
	 * 加密数据类型
	 * data_type=xml或json
	 */
	private String data_type = "json";
	/**
	 * 加密数据
	 */
	private String data_content;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getTxn_type() {
		return txn_type;
	}
	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}
	public String getTxn_sub_type() {
		return txn_sub_type;
	}
	public void setTxn_sub_type(String txn_sub_type) {
		this.txn_sub_type = txn_sub_type;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_content() {
		return data_content;
	}
	public void setData_content(String data_content) {
		this.data_content = data_content;
	}
	

	
}
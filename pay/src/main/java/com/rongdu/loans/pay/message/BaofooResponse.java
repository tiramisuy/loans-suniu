package com.rongdu.loans.pay.message;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 宝付-应答消息
 * @author sunda
 * @version 2017-07-10
 */
public class BaofooResponse extends CreditApiVo{
	
	/**
	 * 版本号
	 */
	private String version;
	/**
	 *请求方保留域
	 */
	private String req_reserved;
	/**
	 *请求方保留域
	 */
	private String additional_info;
	/**
	 *应答码
	 */
	private String resp_code;
	/**
	 *应答信息
	 */
	private String resp_msg;
	/**
	 *商户号
	 */
	private String member_id;
	/**
	 *终端号
	 */
	private String terminal_id;
	/**
	 *加密数据类型
	 */
	private String data_type;
	/**
	 *交易类型
	 */
	private String txn_type;
	/**
	 *交易子类
	 */
	private String txn_sub_type;
	/**
	 *接入类型
	 */
	private String biz_type;
	/**
	 *订单发送时间
	 */
	private String trade_date;
	/**
	 * 商户流水号
	 * 8-20 位字母和数字，每次请求都不可重复(当天和历史均不可重复)
	 */
	private String trans_serial_no;
	/**
	 * 商户订单号
	 *唯一订单号，8-20 位字母和数字，不可重复
	 */
	private String trans_id;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReq_reserved() {
		return req_reserved;
	}
	public void setReq_reserved(String req_reserved) {
		this.req_reserved = req_reserved;
	}
	public String getAdditional_info() {
		return additional_info;
	}
	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_msg() {
		return resp_msg;
	}
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
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
	public String getBiz_type() {
		return biz_type;
	}
	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	public String getTrans_serial_no() {
		return trans_serial_no;
	}
	public void setTrans_serial_no(String trans_serial_no) {
		this.trans_serial_no = trans_serial_no;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	
	@Override
	public boolean isSuccess() {
		return "0000".equals(getCode());
	}
	@Override
	public void setSuccess(boolean success) {
		
	}
	@Override
	public String getCode() {
		return getResp_code();
	}
	@Override
	public void setCode(String code) {
		
	}
	@Override
	public String getMsg() {
		return getResp_msg();
	}
	@Override
	public void setMsg(String msg) {
		
	}

}

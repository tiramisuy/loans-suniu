package com.rongdu.loans.pay.message;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.pay.utils.BaofooConfig;

import java.io.Serializable;

/**
 * 宝付请求报文公共参数
 * @author sunda
 * @version 2017-07-10
 */
public class BaseDataContent implements Serializable{

	private static final long serialVersionUID = 7450487966564228796L;
	
	/**
	 * 交易子类
	 */
	private String txn_sub_type;
	/**
	 * 接入类型
	 */
	private String biz_type = "0000";
	/**
	 * 宝付提供给商户的唯一编号
	 */
	private String member_id = BaofooConfig.member_id;
	/**
	 * 终端号
	 */
	private String terminal_id;
	/**
	 * 商户流水号
	 * 8-20 位字母和数字，每次请求都不可重复(当天和历史均不可重复)
	 */
	private String trans_serial_no = "SN"+System.nanoTime();
	/**
	 * 商户订单号
	 *唯一订单号，8-20 位字母和数字，不可重复
	 */
	private String trans_id;
	/**
	 * 	订单日期
	 * 14 位定长,格式：年年年年月月日日时时分分秒秒
	 */
	private String trade_date = DateUtils.getDate("yyyyMMddHHmmss");
	/**
	 * 	附加字段
	 * 长度不超过 128 位
	 */
	private String additional_info ="";
	/**
	 * 请求方保留域
	 * 选填
	 */
	private String req_reserved = "";
	
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
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	public String getAdditional_info() {
		return additional_info;
	}
	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}
	public String getReq_reserved() {
		return req_reserved;
	}
	public void setReq_reserved(String req_reserved) {
		this.req_reserved = req_reserved;
	}

}
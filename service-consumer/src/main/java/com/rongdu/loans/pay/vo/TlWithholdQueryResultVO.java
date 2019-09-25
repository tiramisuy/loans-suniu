package com.rongdu.loans.pay.vo;

import com.rongdu.common.exception.ErrInfo;

import java.io.Serializable;

/**
 * 应答消息
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/8
 * 
 */
public class TlWithholdQueryResultVO implements Serializable {

	private static final long serialVersionUID = 5451809413349822648L;
	/**
	 * 是否成功
	 */
	private Boolean success = false;
	private String reqNo;
	private String sn;
	/**
	 * 应答代码
	 */
	private String code;
	/**
	 * 应答消息
	 */
	private String msg;
	/**
	 * 通联交易号
	 */
	private String transNo;
	/**
	 * 成功金额
	 */
	private String succAmt;
	/**
	 * 原始商户订单号(还款明细ID)
	 * 商户提交的标识支付的唯一原订单号
	 */
	private String origTransId;
	/**
	 * 订单日期
	 * 14 位定长。格式：年年年年月月日日时时分分秒秒
	 */
	private String origTradeDate;
	/**
	 * 商户流水号
	 */
	private String transSerialNo;
	/**
	 * 交易结果状态码
	 */
	private String orderStat;

	/**
	 * 购物券id(多个逗号分隔)
	 */
	private String couponId;

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public TlWithholdQueryResultVO() {
		this.code = ErrInfo.ERROR.getCode();
		this.msg = ErrInfo.ERROR.getMsg();
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(String succAmt) {
		this.succAmt = succAmt;
	}

	public String getOrigTransId() {
		return origTransId;
	}

	public void setOrigTransId(String origTransId) {
		this.origTransId = origTransId;
	}

	public String getOrigTradeDate() {
		return origTradeDate;
	}

	public void setOrigTradeDate(String origTradeDate) {
		this.origTradeDate = origTradeDate;
	}

	public String getTransSerialNo() {
		return transSerialNo;
	}

	public void setTransSerialNo(String transSerialNo) {
		this.transSerialNo = transSerialNo;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
}

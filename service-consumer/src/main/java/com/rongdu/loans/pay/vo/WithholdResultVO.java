package com.rongdu.loans.pay.vo;

import java.io.Serializable;

import com.rongdu.common.exception.ErrInfo;

/**
 * 直接绑卡应答消息
 * @author sunda
 * @version 2017-07-20
 */
public class WithholdResultVO implements Serializable {

	private static final long serialVersionUID = 5451809413349822648L;
	/**
	 * 是否成功
	 */
	private Boolean success = false;
	/**
	 * 应答代码
	 */
	private String code;
	/**
	 * 应答消息
	 */
	private String msg;
	/**
	 * 宝付，通联交易号
	 */
	private String transNo;
	/**
	 * 成功金额
	 */
	private String succAmt;
	/**
	 * 商户订单号
	 *唯一订单号，8-20 位字母和数字，不可重复
	 */
	private String transId;
	/**
	 *订单发送时间
	 */
	private String tradeDate;
	/**
	 * 商户流水号
	 * 8-20 位字母和数字，每次请求都不可重复(当天和历史均不可重复)
	 */
	private String transSerialNo;
	
	/**
	 * code y0524
	 * 是否处理中
	 */
	private Boolean unsolved = false;

	/**
	 * 购物券id(多个逗号分隔)
	 */
	private String couponId;

	private String status;

	private String payType;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public WithholdResultVO() {
		this.code = ErrInfo.ERROR.getCode();
		this.msg = ErrInfo.ERROR.getMsg();
	}

	public Boolean getUnsolved() {
		return unsolved;
	}

	public void setUnsolved(Boolean unsolved) {
		this.unsolved = unsolved;
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

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTransSerialNo() {
		return transSerialNo;
	}

	public void setTransSerialNo(String transSerialNo) {
		this.transSerialNo = transSerialNo;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}

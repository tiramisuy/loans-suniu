package com.rongdu.loans.pay.vo;

import java.io.Serializable;

import com.rongdu.common.exception.ErrInfo;

/**
 * 直接绑卡应答消息
 * 
 * @author sunda
 * @version 2017-07-20
 */
public class BindCardResultVO implements Serializable {

	private static final long serialVersionUID = 5451809413349822648L;
	/**
	 * 是否成功
	 */
	protected boolean success = false;
	/**
	 * 应答代码
	 */
	protected String code;
	/**
	 * 应答消息
	 */
	protected String msg;
	/**
	 * 预绑卡交易订单号
	 */
	protected String orderNo;
	/**
	 * 绑定号
	 */
	protected String bindId;
	/**
	 * 银行编码
	 */
	private String bankCode;
	/**
	 * 银行名称
	 */
	private String bankName;

	public BindCardResultVO() {
		this.code = ErrInfo.ERROR.getCode();
		this.msg = ErrInfo.ERROR.getMsg();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}

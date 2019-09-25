package com.rongdu.loans.pay.vo;

import java.io.Serializable;

import com.rongdu.common.exception.ErrInfo;

/**
 * 预支付返回对象
 * @author likang
 */
public class PreAuthPayVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2821703359057286831L;
	
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
 	 * 支付渠道的交易订单号
	 */
	private String chlOrderNo;

	public PreAuthPayVO() {
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

	public String getChlOrderNo() {
		return chlOrderNo;
	}

	public void setChlOrderNo(String chlOrderNo) {
		this.chlOrderNo = chlOrderNo;
	}

}

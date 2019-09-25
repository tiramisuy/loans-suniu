package com.rongdu.loans.pay.op;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 确认支付入参对象
 * 
 * @author likang
 * 
 */
public class ConfirmAuthPayOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2620786142378971777L;

	/**
	 * 预支付后推送的短信验证码
	 */
	@NotNull(message = "短信验证码不能为空")
	private String smsCode;

	/**
	 * 支付订单号 （预支付后返回订单号）
	 */
	@NotNull(message = "支付订单号不能为空")
	private String chlOrderNo;

	/**
	 * 支付类型 1=还款 2=延期
	 */
	private Integer payType;

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getChlOrderNo() {
		return chlOrderNo;
	}

	public void setChlOrderNo(String chlOrderNo) {
		this.chlOrderNo = chlOrderNo;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

}

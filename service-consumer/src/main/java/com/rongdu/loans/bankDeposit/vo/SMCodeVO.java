package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

/**
 * 返回对象
 * @author likang
 *
 */
public class SMCodeVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6545886240215332500L;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 业务授权码
	 */
	private String srvAuthCode;
	
	/**
	 * 短信序列号
	 */
	private String smsSeq;

	public String getMobile() {
		return mobile;
	}

	public String getSrvAuthCode() {
		return srvAuthCode;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setSrvAuthCode(String srvAuthCode) {
		this.srvAuthCode = srvAuthCode;
	}

	public String getSmsSeq() {
		return smsSeq;
	}

	public void setSmsSeq(String smsSeq) {
		this.smsSeq = smsSeq;
	}
}

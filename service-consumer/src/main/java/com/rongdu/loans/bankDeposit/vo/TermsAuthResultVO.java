package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

public class TermsAuthResultVO implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3879253513581062643L;
	/**
     * 电子账号
     */
    private String accountId;
    
    /**
     * 持卡人姓名
     */
    private String name;

    /**
     * 签约订单号
     */
    private String orderId;

	public String getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

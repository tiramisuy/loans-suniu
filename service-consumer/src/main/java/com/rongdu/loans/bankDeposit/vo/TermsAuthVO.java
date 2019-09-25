package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

public class TermsAuthVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4986755548022780137L;

    /**
     * 应答码
     */
    private String code;

    /**
     * 应答码描述
     */
    private String message;

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
    
    /**
     * 结果对象
     */
    private TermsAuthResultVO result;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public String getOrderId() {
		return orderId;
	}

	public TermsAuthResultVO getResult() {
		return result;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public void setResult(TermsAuthResultVO result) {
		this.result = result;
	} 
}

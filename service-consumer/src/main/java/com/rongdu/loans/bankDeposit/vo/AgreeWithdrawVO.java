package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

public class AgreeWithdrawVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -1056766654425804777L;

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
     * 交易金额
     */
    private String txAmount;

    /**
     * 数据对象
     */
    private AgreeWithdrawResultVO result;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getTxAmount() {
		return txAmount;
	}

	public AgreeWithdrawResultVO getResult() {
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

	public void setTxAmount(String txAmount) {
		this.txAmount = txAmount;
	}

	public void setResult(AgreeWithdrawResultVO result) {
		this.result = result;
	}
}

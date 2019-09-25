package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

public class AuthQueryVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2832208572305901843L;
	/**
     * 应答码
     */
    private String code;

    /**
     * 应答码描述
     */
    private String message;
    
	/**
	 * result
	 */
	private AuthQueryResultVO result;
	
	/**
	 * 自动投标开通状态
	 */
	private String autoBidStatus;
	
	/**
	 *  自动债转开通状态
	 */
	private String autoTransferStatus;
	
	
	/**
	 * 预约取现开通状态
	 */
	private String agreeWithdrawStatus;
	
	/**
	 * 无密消费开通状态
	 */
	private String agreeDeductStatus;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AuthQueryResultVO getResult() {
		return result;
	}

	public void setResult(AuthQueryResultVO result) {
		this.result = result;
	}

	public String getAutoBidStatus() {
		return autoBidStatus;
	}

	public void setAutoBidStatus(String autoBidStatus) {
		this.autoBidStatus = autoBidStatus;
	}

	public String getAutoTransferStatus() {
		return autoTransferStatus;
	}

	public void setAutoTransferStatus(String autoTransferStatus) {
		this.autoTransferStatus = autoTransferStatus;
	}

	public String getAgreeWithdrawStatus() {
		return agreeWithdrawStatus;
	}

	public void setAgreeWithdrawStatus(String agreeWithdrawStatus) {
		this.agreeWithdrawStatus = agreeWithdrawStatus;
	}

	public String getAgreeDeductStatus() {
		return agreeDeductStatus;
	}

	public void setAgreeDeductStatus(String agreeDeductStatus) {
		this.agreeDeductStatus = agreeDeductStatus;
	}
}

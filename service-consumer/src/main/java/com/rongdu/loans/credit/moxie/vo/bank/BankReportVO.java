package com.rongdu.loans.credit.moxie.vo.bank;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;

public class BankReportVO extends CreditApiVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086604470230375752L;
	private String code;
	private String msg;

	private String message;
	private String update_time;

	// 借记卡
	private BankReportDebitcardVO debitcard;
	// 信用卡
	private BankReportCreditcardVO creditcard;

	public boolean isSuccess() {
		if ("0".equals(code)) {
			return true;
		}
		return false;
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
		return message;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public BankReportDebitcardVO getDebitcard() {
		return debitcard;
	}

	public void setDebitcard(BankReportDebitcardVO debitcard) {
		this.debitcard = debitcard;
	}

	public BankReportCreditcardVO getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(BankReportCreditcardVO creditcard) {
		this.creditcard = creditcard;
	}

}

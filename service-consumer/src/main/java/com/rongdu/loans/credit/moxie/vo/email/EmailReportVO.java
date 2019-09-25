package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;

public class EmailReportVO extends CreditApiVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086604470230375752L;
	private String code;
	private String msg;

	private String message;
	private String update_time;
	/**
	 * 1.1用户基本信息(user_basic_information)
	 */
	private EmailReportUserBasicInformation user_basic_information;
	/**
	 * 1.2授信情况(credit_limit_informatin)
	 */
	private EmailReportCreditLimitInformatin credit_limit_informatin;
	/**
	 * 1.3交易行为(transaction_information)
	 */
	private EmailReportTransactionInformation transaction_information;
	/**
	 * 1.4还款(payment_information)
	 */
	private EmailReportPaymentInformation payment_information;
	/**
	 * 1.5逾期信息(overdue_information)
	 */
	private EmailReportOverdueInformation overdue_information;
	/**
	 * 1.6利费信息(interest_fee_information)
	 */
	private EmailReportInterestFeeInformation interest_fee_information;
	/**
	 * 2.1销售金额(new_charge_information)
	 */
	private EmailReportNewChargeInformation new_charge_information;
	/**
	 * 2.2余额(new_balance_information)
	 */
	private EmailReportNewBalanceInformation new_balance_information;
	/**
	 * 2.3还款(payment_analyze_information)
	 */
	private EmailReportPaymentAnalyzeInformation payment_analyze_information;
	/**
	 * 2.4额度(credit_limit_analyze_informatin)
	 */
	private EmailReportCreditLimitAnalyzeInformatin credit_limit_analyze_informatin;
	/**
	 * 2.5利息(interest_fee_analyze_information)
	 */
	private EmailReportInterestFeeAnalyzeInformation interest_fee_analyze_information;
	/**
	 * 2.6收入(incoming_information)
	 */
	private EmailReportIncomingInformation incoming_information;
	/**
	 * 2.7超额(excess_information)
	 */
	private EmailReportExcessInformation excess_information;
	/**
	 * 2.8逾期(overdue_analyze_information)
	 */
	private EmailReportOverdueAnalyzeInformation overdue_analyze_information;
	/**
	 * 2.9固定属性(fix_attribute_information)
	 */
	private EmailReportFixAttributeInformation fix_attribute_information;

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

	public EmailReportUserBasicInformation getUser_basic_information() {
		return user_basic_information;
	}

	public void setUser_basic_information(EmailReportUserBasicInformation user_basic_information) {
		this.user_basic_information = user_basic_information;
	}

	public EmailReportCreditLimitInformatin getCredit_limit_informatin() {
		return credit_limit_informatin;
	}

	public void setCredit_limit_informatin(EmailReportCreditLimitInformatin credit_limit_informatin) {
		this.credit_limit_informatin = credit_limit_informatin;
	}

	public EmailReportTransactionInformation getTransaction_information() {
		return transaction_information;
	}

	public void setTransaction_information(EmailReportTransactionInformation transaction_information) {
		this.transaction_information = transaction_information;
	}

	public EmailReportPaymentInformation getPayment_information() {
		return payment_information;
	}

	public void setPayment_information(EmailReportPaymentInformation payment_information) {
		this.payment_information = payment_information;
	}

	public EmailReportOverdueInformation getOverdue_information() {
		return overdue_information;
	}

	public void setOverdue_information(EmailReportOverdueInformation overdue_information) {
		this.overdue_information = overdue_information;
	}

	public EmailReportInterestFeeInformation getInterest_fee_information() {
		return interest_fee_information;
	}

	public void setInterest_fee_information(EmailReportInterestFeeInformation interest_fee_information) {
		this.interest_fee_information = interest_fee_information;
	}

	public EmailReportNewChargeInformation getNew_charge_information() {
		return new_charge_information;
	}

	public void setNew_charge_information(EmailReportNewChargeInformation new_charge_information) {
		this.new_charge_information = new_charge_information;
	}

	public EmailReportNewBalanceInformation getNew_balance_information() {
		return new_balance_information;
	}

	public void setNew_balance_information(EmailReportNewBalanceInformation new_balance_information) {
		this.new_balance_information = new_balance_information;
	}

	public EmailReportPaymentAnalyzeInformation getPayment_analyze_information() {
		return payment_analyze_information;
	}

	public void setPayment_analyze_information(EmailReportPaymentAnalyzeInformation payment_analyze_information) {
		this.payment_analyze_information = payment_analyze_information;
	}

	public EmailReportCreditLimitAnalyzeInformatin getCredit_limit_analyze_informatin() {
		return credit_limit_analyze_informatin;
	}

	public void setCredit_limit_analyze_informatin(
			EmailReportCreditLimitAnalyzeInformatin credit_limit_analyze_informatin) {
		this.credit_limit_analyze_informatin = credit_limit_analyze_informatin;
	}

	public EmailReportInterestFeeAnalyzeInformation getInterest_fee_analyze_information() {
		return interest_fee_analyze_information;
	}

	public void setInterest_fee_analyze_information(
			EmailReportInterestFeeAnalyzeInformation interest_fee_analyze_information) {
		this.interest_fee_analyze_information = interest_fee_analyze_information;
	}

	public EmailReportIncomingInformation getIncoming_information() {
		return incoming_information;
	}

	public void setIncoming_information(EmailReportIncomingInformation incoming_information) {
		this.incoming_information = incoming_information;
	}

	public EmailReportExcessInformation getExcess_information() {
		return excess_information;
	}

	public void setExcess_information(EmailReportExcessInformation excess_information) {
		this.excess_information = excess_information;
	}

	public EmailReportOverdueAnalyzeInformation getOverdue_analyze_information() {
		return overdue_analyze_information;
	}

	public void setOverdue_analyze_information(EmailReportOverdueAnalyzeInformation overdue_analyze_information) {
		this.overdue_analyze_information = overdue_analyze_information;
	}

	public EmailReportFixAttributeInformation getFix_attribute_information() {
		return fix_attribute_information;
	}

	public void setFix_attribute_information(EmailReportFixAttributeInformation fix_attribute_information) {
		this.fix_attribute_information = fix_attribute_information;
	}

}

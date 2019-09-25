package com.rongdu.loans.credit.moxie.vo.bank;

import java.io.Serializable;

import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardAccountSummary;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardBalance;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardCreditCardSummary;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardIncome;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardInterest;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardInterestInformation;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardOtherAttribute;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardOverdueCreditcard;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardOverdueInformation;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardOverrun;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardQuota;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardRepayment;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardRepaymentSummary;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardSalesAmount;
import com.rongdu.loans.credit.moxie.vo.bank.creditcard.CreditcardUserBasicInfoVO;

public class BankReportCreditcardVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 1.1 用户基本信息 （user_basic_info）
	private CreditcardUserBasicInfoVO user_basic_info;
	// 1.2 授信情况 account_summary
	private CreditcardAccountSummary account_summary;
	// 1.3 交易行为 (credit_card_summary)
	private CreditcardCreditCardSummary credit_card_summary;
	// 1.4 还款 (repayment_summary)
	private CreditcardRepaymentSummary repayment_summary;
	// 1.5 逾期信息 (overdue_information)
	private CreditcardOverdueInformation overdue_information;
	// 1.6 利费信息 (interest_information)
	private CreditcardInterestInformation interest_information;
	// 2.1 销售金额 (sales_amount)
	private CreditcardSalesAmount sales_amount;
	// 2.2 余额 (balance)
	private CreditcardBalance balance;
	// 2.3 还款 (repayment)
	private CreditcardRepayment repayment;
	// 2.4 额度 (quota)
	private CreditcardQuota quota;
	// 2.5 利息 (interest)
	private CreditcardInterest interest;
	// 2.6 收入 (income)
	private CreditcardIncome income;
	// 2.7 超额 (overrun)
	private CreditcardOverrun overrun;
	// 2.8 逾期 (overdue_creditcard)
	private CreditcardOverdueCreditcard overdue_creditcard;
	// 2.9 固定属性 (other_attribute)
	private CreditcardOtherAttribute other_attribute;

	public CreditcardUserBasicInfoVO getUser_basic_info() {
		return user_basic_info;
	}

	public void setUser_basic_info(CreditcardUserBasicInfoVO user_basic_info) {
		this.user_basic_info = user_basic_info;
	}

	public CreditcardAccountSummary getAccount_summary() {
		return account_summary;
	}

	public void setAccount_summary(CreditcardAccountSummary account_summary) {
		this.account_summary = account_summary;
	}

	public CreditcardCreditCardSummary getCredit_card_summary() {
		return credit_card_summary;
	}

	public void setCredit_card_summary(CreditcardCreditCardSummary credit_card_summary) {
		this.credit_card_summary = credit_card_summary;
	}

	public CreditcardRepaymentSummary getRepayment_summary() {
		return repayment_summary;
	}

	public void setRepayment_summary(CreditcardRepaymentSummary repayment_summary) {
		this.repayment_summary = repayment_summary;
	}

	public CreditcardOverdueInformation getOverdue_information() {
		return overdue_information;
	}

	public void setOverdue_information(CreditcardOverdueInformation overdue_information) {
		this.overdue_information = overdue_information;
	}

	public CreditcardInterestInformation getInterest_information() {
		return interest_information;
	}

	public void setInterest_information(CreditcardInterestInformation interest_information) {
		this.interest_information = interest_information;
	}

	public CreditcardSalesAmount getSales_amount() {
		return sales_amount;
	}

	public void setSales_amount(CreditcardSalesAmount sales_amount) {
		this.sales_amount = sales_amount;
	}

	public CreditcardBalance getBalance() {
		return balance;
	}

	public void setBalance(CreditcardBalance balance) {
		this.balance = balance;
	}

	public CreditcardRepayment getRepayment() {
		return repayment;
	}

	public void setRepayment(CreditcardRepayment repayment) {
		this.repayment = repayment;
	}

	public CreditcardQuota getQuota() {
		return quota;
	}

	public void setQuota(CreditcardQuota quota) {
		this.quota = quota;
	}

	public CreditcardInterest getInterest() {
		return interest;
	}

	public void setInterest(CreditcardInterest interest) {
		this.interest = interest;
	}

	public CreditcardIncome getIncome() {
		return income;
	}

	public void setIncome(CreditcardIncome income) {
		this.income = income;
	}

	public CreditcardOverrun getOverrun() {
		return overrun;
	}

	public void setOverrun(CreditcardOverrun overrun) {
		this.overrun = overrun;
	}

	public CreditcardOverdueCreditcard getOverdue_creditcard() {
		return overdue_creditcard;
	}

	public void setOverdue_creditcard(CreditcardOverdueCreditcard overdue_creditcard) {
		this.overdue_creditcard = overdue_creditcard;
	}

	public CreditcardOtherAttribute getOther_attribute() {
		return other_attribute;
	}

	public void setOther_attribute(CreditcardOtherAttribute other_attribute) {
		this.other_attribute = other_attribute;
	}

}

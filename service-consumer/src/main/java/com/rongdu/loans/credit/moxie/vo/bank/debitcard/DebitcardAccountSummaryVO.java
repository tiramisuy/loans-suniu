package com.rongdu.loans.credit.moxie.vo.bank.debitcard;

import java.io.Serializable;

/**
 * 1.2 账户摘要 （account_summary）
 * 
 * @author liuzhuang
 * 
 */
public class DebitcardAccountSummaryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String debitcard_num;// 借记卡数 2
	private String debitcard_balance;// 借记卡余额（元） 423.74

	public String getDebitcard_num() {
		return debitcard_num;
	}

	public void setDebitcard_num(String debitcard_num) {
		this.debitcard_num = debitcard_num;
	}

	public String getDebitcard_balance() {
		return debitcard_balance;
	}

	public void setDebitcard_balance(String debitcard_balance) {
		this.debitcard_balance = debitcard_balance;
	}

}

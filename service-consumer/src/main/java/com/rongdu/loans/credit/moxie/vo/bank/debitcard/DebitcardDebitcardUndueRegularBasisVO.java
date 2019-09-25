package com.rongdu.loans.credit.moxie.vo.bank.debitcard;

import java.io.Serializable;

/**
 * 2.2 借记卡未到期定期详情 （debitcard_undue_regular_basis_list）
 * 
 * @author liuzhuang
 * 
 */
public class DebitcardDebitcardUndueRegularBasisVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cardId;// 卡号 6214000012347654
	private String balance;// 金额 4237.00
	private String duedate;// 到期日期 2017-01-12
	private String period;// 期数 6

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}

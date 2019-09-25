package com.rongdu.loans.api.web.option;

import java.io.Serializable;

public class KouDaiLcRequestContentOP implements Serializable {
	private static final long serialVersionUID = -1;

	/**
	 * 放款时间
	 */
	private Integer loanTime;
	/**
	 * 
	 */
	private Integer withdrawTime;

	public Integer getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Integer loanTime) {
		this.loanTime = loanTime;
	}

	public Integer getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Integer withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

}

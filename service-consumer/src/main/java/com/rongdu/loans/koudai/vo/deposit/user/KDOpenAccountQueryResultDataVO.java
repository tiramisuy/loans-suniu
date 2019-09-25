package com.rongdu.loans.koudai.vo.deposit.user;

import java.io.Serializable;

public class KDOpenAccountQueryResultDataVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountId;// 电子账户 string
	private String bindCard;// 绑定银行卡 string
	private String mobile;// 手机号 string
	private String isBindCard;// 是否绑卡 string 0 未绑卡 1已绑卡

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBindCard() {
		return bindCard;
	}

	public void setBindCard(String bindCard) {
		this.bindCard = bindCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsBindCard() {
		return isBindCard;
	}

	public void setIsBindCard(String isBindCard) {
		this.isBindCard = isBindCard;
	}

}

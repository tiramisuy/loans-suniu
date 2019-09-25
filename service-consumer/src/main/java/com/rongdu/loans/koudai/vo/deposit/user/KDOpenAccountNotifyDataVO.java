package com.rongdu.loans.koudai.vo.deposit.user;

import java.io.Serializable;

public class KDOpenAccountNotifyDataVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountId;// 电子账户
	private String idNum;// 身份证
	private String cardNo;// 绑定银行卡
	private String mobile;// 手机号码
	private String openMobile;// 开户时所用手机号

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenMobile() {
		return openMobile;
	}

	public void setOpenMobile(String openMobile) {
		this.openMobile = openMobile;
	}

}

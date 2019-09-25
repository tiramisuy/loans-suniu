package com.rongdu.loans.koudai.api.vo.deposit.user;

import java.io.Serializable;

public class KDPwdResetApiDataOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idNo;
	private String retUrl; // 同步跳转地址 不能长于256个字符，否则银行校验失败
	private int isUrl = 1; // 是否需要 1或者0 地址只能使用一次，并且3分钟内有效

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public int getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(int isUrl) {
		this.isUrl = isUrl;
	}

}

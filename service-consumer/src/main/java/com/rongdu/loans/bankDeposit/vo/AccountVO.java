package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

/**
 * Created by likang on 2017/8/14.
 */
public class AccountVO implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -4215697614945297128L;
    
    /**
     * 返回码
     */
    private String retCode;
    
    /**
     * 返回信息
     */
    private String retMsg;
    
    /**
     * 电子账户
     */
    private String accountId;
    
    /**
     * 开通日期
     */
    private String openDate;
    
    /**
     * 银行编号
     */
    private String bankCode;
    
    /**
     * 证件号类型
     */
    private String idType;
    
    /**
     * 证件号
     */
    private String idNo;
    
    public String getRetCode() {
		return retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getOpenDate() {
		return openDate;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getIdType() {
		return idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}

package com.rongdu.loans.tongrong.op;

import java.io.Serializable;

public class TRUserBasicInfo implements Serializable{

	private static final long serialVersionUID = 2434322855370569064L;

	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 手机号
	 */
	private String mobileNo;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 开户行
	 */
	private String bankCardName;
	/**
	 * 身份证地址
	 */
	private String idAddress;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getIdAddress() {
		return idAddress;
	}
	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}
	
	
}

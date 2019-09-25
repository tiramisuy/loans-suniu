package com.rongdu.loans.koudai.op.create;

import java.io.Serializable;

public class KDDebitCardOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer bank_id;//扣款银行卡ID
	private String card_no;//银行卡号
	private Long phone;//银行卡预留手机号
	private String name;//持卡人姓名
	private String bank_address;//开户行地址
	
	public Integer getBank_id() {
		return bank_id;
	}
	public void setBank_id(Integer bank_id) {
		this.bank_id = bank_id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBank_address() {
		return bank_address;
	}
	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}
	
	
}

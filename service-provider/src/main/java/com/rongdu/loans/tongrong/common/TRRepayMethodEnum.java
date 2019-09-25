package com.rongdu.loans.tongrong.common;

public enum TRRepayMethodEnum {
	
	CODE_1("1", "等额本息"),
	CODE_2("2", "到期还本付息"),
	CODE_3("3", "等本等息"),
	CODE_4("4", "先息后本");
	
	
	private String code;
	private String desc;
	
	private TRRepayMethodEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}

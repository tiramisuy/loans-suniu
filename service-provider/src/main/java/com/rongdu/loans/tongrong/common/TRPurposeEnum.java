package com.rongdu.loans.tongrong.common;

public enum TRPurposeEnum {
	
	CODE_1("1", "装修"),
	CODE_2("2", "个人日常消费"),
	CODE_3("3", "教育"),
	CODE_4("4", "出国留学"),
	CODE_5("5", "旅游"),
	CODE_6("6", "经营资金流转"),
	CODE_7("7", "其他消费"),
	CODE_8("8", "医疗"),
	CODE_9("9", "婚庆");
	
	
	private String code;
	private String desc;
	
	private TRPurposeEnum(String code, String desc) {
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

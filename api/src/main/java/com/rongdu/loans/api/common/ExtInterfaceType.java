package com.rongdu.loans.api.common;

public enum ExtInterfaceType {

	OCR(1, "ocr"),
	FACE(2, "人脸识别"),
	TEL_OPERATOR(3, "电信运营商"),
	SESAME_CREDIT(4, "芝麻信用"),
	CREDIT_CARD(5, "信用卡");
	
	private Integer value;
	private String desc;
	
	ExtInterfaceType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	 
	public Integer getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}

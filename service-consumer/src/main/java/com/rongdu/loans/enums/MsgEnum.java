package com.rongdu.loans.enums;

public enum MsgEnum {

	SEND_WAIT(0, "待发送"),
	SEND_SUCCSS(1, "发送成功"),
	SEND_FAIL(2, "发送失败");
	
	private Integer value;
	private String desc;
	
	MsgEnum(Integer value, String desc) {
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

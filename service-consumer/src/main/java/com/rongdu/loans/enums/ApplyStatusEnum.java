package com.rongdu.loans.enums;

public enum ApplyStatusEnum {

	UNFINISH(0, "未完结"),
	FINISHED(1, "已完结"),
	INITIAL(2, "初始"),
	PROCESSING(3, "处理中");

	private Integer value;
	private String desc;

	ApplyStatusEnum(Integer value, String desc) {
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

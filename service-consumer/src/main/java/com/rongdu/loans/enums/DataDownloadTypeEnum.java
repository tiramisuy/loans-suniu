package com.rongdu.loans.enums;

public enum DataDownloadTypeEnum {

	CONTRACT(1, "合同"),
	EXPORT_DATA(2, "导出数据");
	
	private Integer value;
	private String desc;
	
	DataDownloadTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	
    public static Integer getCode(String desc) {
        for (DataDownloadTypeEnum temp : DataDownloadTypeEnum.values()) {
            if (temp.getDesc().equals(desc)) {
                return temp.getValue();
            }
        }
        return null;
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

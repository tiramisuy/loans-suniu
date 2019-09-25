package com.rongdu.loans.enums;

import com.rongdu.common.config.Global;

public enum MsgViewStatus {

	VIEWED(Global.CUST_MESSAGE_VIEW_STATUS_1, "已读"),
	UNVIEW(Global.CUST_MESSAGE_VIEW_STATUS_0, "未读");
	
	private Integer value;
	private String desc;
	
	MsgViewStatus(Integer value, String desc) {
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

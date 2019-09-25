package com.rongdu.loans.enums;

public enum MsgTypeEnum {

	REG(1, "fnReg"),
	FORGET(2, "fnForget"),
	PUSH(3, "fnPush"),
	REPAY_NOTICE(4,"notice"); //还款通知
	
	private Integer value;
	private String desc;
	
	MsgTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
	
    /**
     * 根据id匹配短信类型码
     * @param desc
     * @return
     */
    public static Integer getCode(String desc) {
        for (MsgTypeEnum temp : MsgTypeEnum.values()) {
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

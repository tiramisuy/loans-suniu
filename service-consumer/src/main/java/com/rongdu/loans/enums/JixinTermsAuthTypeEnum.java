package com.rongdu.loans.enums;

public enum JixinTermsAuthTypeEnum {

	AUTOBID(1, "autoBid", "自动投标功能"),
	AUTOTRANSFER(2, "autoTransfer", "自动债转功能"),
	AGREEWITHDRAW(3, "agreeWithdraw", "预约取现功能"),
	AGREEDEDUCT(4, "agreeDeduct", "无密消费功能");
	
	private Integer code;
	private String flags;
	private String desc;
	
	JixinTermsAuthTypeEnum(Integer code, String flags, String desc) {
        this.code = code;
        this.desc = desc;
    }
	 
    public static String getDesc(Integer code) {
        for (JixinTermsAuthTypeEnum temp : JixinTermsAuthTypeEnum.values()) {
            if (temp.getCode().equals(code)) {
                return temp.getDesc();
            }
        }
        return null;
    }
    
    public static Integer getCode(Integer flags) {
        for (JixinTermsAuthTypeEnum temp : JixinTermsAuthTypeEnum.values()) {
            if (temp.getFlags().equals(flags)) {
                return temp.getCode();
            }
        }
        return null;
    }
    

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}

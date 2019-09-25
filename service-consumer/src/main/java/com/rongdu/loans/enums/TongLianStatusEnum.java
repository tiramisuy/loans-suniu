package com.rongdu.loans.enums;

public enum TongLianStatusEnum {
    TONGLIAN_WITHDRAWAL_SUCCESS("S", "提现成功"),
    TONGLIAN_WITHDRAWAL_FAILED("F", "提现失败"),
    TONGLIAN_WITHDRAWAL_PROCC("I", "提现处理中");

    private String value;
    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    TongLianStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}

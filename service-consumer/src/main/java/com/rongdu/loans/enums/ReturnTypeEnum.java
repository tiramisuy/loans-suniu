package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
public enum ReturnTypeEnum {
    TIMING(1,"定时退回"),
    NOT(2,"不退回")
    ;
    private Integer value;
    private String desc;

    ReturnTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
    

}

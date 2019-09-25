package com.rongdu.loans.enums;

/**
 * 支付类型枚举
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
public enum PayTypesEnum {

    TONGLIAN("tonglian", "通联"),
    BAOFU("baofu", "宝付"),
    XIANFENG("xianfeng", "先锋");


    private String code;
    private String desc;

    PayTypesEnum(String code, String desc) {
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

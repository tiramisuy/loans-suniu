package com.rongdu.loans.enums;

/**
 * 购物券抵扣
 * 
 * Created by weiyang on 2018/12/7
 * 
 */
public enum RepayDeductionTypeEnum {
    YES("1","抵扣"),
    NO("0","不抵扣")
    ;
    private String value;
    private String desc;

    RepayDeductionTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
    

}

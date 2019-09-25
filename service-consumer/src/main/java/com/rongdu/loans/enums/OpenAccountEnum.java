package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
public enum OpenAccountEnum {
    ALL(-1,"全部"),
    NO(0,"未开户"),
    YES(1,"已开户")
    ;
    private Integer value;
    private String desc;

    OpenAccountEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (OpenAccountEnum openAccountEnum : OpenAccountEnum.values()) {
            if (openAccountEnum.getValue().equals(id)) {
                return openAccountEnum.getDesc();
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
}

package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
public enum WhetherEnum {

    ALL("","全部"),
    NO("0","否"),
    YES("1","是")
    ;
    private String value;
    private String desc;

    WhetherEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(String id) {
        for (WhetherEnum whetherEnum : WhetherEnum.values()) {
            if (whetherEnum.getValue().equals(id)) {
                return whetherEnum.getDesc();
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}

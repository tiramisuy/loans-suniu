package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/6/30.
 */
public enum IdTypeEnum {

    Id_0("0","身份证"),
    Id_1("1","户口簿"),
    Id_2("2","护照"),
    Id_3("3","军官证"),
    Id_4("4","士兵证"),
    Id_5("5","港澳居民来往内地通行证"),
    Id_6("6","台湾同胞来往内地通行证"),
    Id_7("7","临时身份证"),
    Id_8("8","外国人居留证"),
    Id_9("9","警官证"),
    Id_X("X","其他证件")
    ;
    private String value;
    private String desc;

    IdTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(String id) {
        for (IdTypeEnum idTypeEnum : IdTypeEnum.values()) {
            if (idTypeEnum.getValue().equals(id)) {
                return idTypeEnum.getDesc();
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

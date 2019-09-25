package com.rongdu.loans.enums;

/**
 * Created by zhangxiaolong on 2017/7/4.
 */
public enum MaritalEnum {
    MARRIED(1,"已婚"),
    UNMARRIED(2, "未婚"), 
    DIVORCE(3, "离异"),
    DEATH(4,"丧偶")
    ;
    private Integer value;
    private String desc;

    MaritalEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (MaritalEnum maritalEnum : MaritalEnum.values()) {
            if (maritalEnum.getValue().equals(id)) {
                return maritalEnum.getDesc();
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

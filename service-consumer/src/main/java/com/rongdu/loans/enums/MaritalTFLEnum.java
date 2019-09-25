package com.rongdu.loans.enums;

/**
 * Created by liuzhuang on 2018/1/25.
 */
public enum MaritalTFLEnum {
    MARRIED(1,"已婚"),
    DIVORCE(2,"离异"),
    UNMARRIED(3,"未婚");
    
    private Integer value;
    private String desc;

    MaritalTFLEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (MaritalTFLEnum maritalEnum : MaritalTFLEnum.values()) {
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

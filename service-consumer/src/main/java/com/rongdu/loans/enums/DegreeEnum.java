package com.rongdu.loans.enums;

public enum DegreeEnum {

	MASTER_AND_UP(1,"研究生及以上"),
	UNDERGRADUATE(2,"本科"),
	ACADEMY(3,"大专"),
	SENIOR_MIDDLE_SCHOOL_AND_BELOW(4,"高中及以下");
	
    private Integer value;
    private String desc;

    DegreeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (DegreeEnum degreeEnum : DegreeEnum.values()) {
            if (degreeEnum.getValue().equals(id)) {
                return degreeEnum.getDesc();
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

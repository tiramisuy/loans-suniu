package com.rongdu.loans.enums;

import com.rongdu.common.utils.StringUtils;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
public enum SexEnum {
    MALE(1,"男"),
    FEMALE(2,"女")
    ;
    private Integer value;
    private String desc;

    SexEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getValue().equals(id)) {
                return sexEnum.getDesc();
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
    
    public static Integer getValue(String desc) {
        if(StringUtils.equals(desc, FEMALE.getDesc()) 
        		|| StringUtils.equals(desc, "2")) {
        	return FEMALE.getValue();
        }
        return MALE.getValue();
    }
}

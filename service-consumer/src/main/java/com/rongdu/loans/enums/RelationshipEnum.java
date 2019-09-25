package com.rongdu.loans.enums;

import com.rongdu.common.utils.StringUtils;

/**
 * Created by zhangxiaolong on 2017/7/4.
 */
public enum RelationshipEnum {
    BAIQISHI(0,"白骑士"),
    PARENTS(1,"父母"),
    SPOUSE(2,"配偶"),
    FRIEND(3,"朋友"),
    COLLEAGUES(4,"同事"),
    MOTHER(5,"母亲"),
    SELF(6,"本人"),
    OTHER(7,"其他联系人");
    private Integer value;
    private String desc;

    RelationshipEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer id) {
        for (RelationshipEnum relationshipEnum : RelationshipEnum.values()) {
            if (relationshipEnum.getValue().equals(id)) {
                return relationshipEnum.getDesc();
            }
        }
        return null;
    }

    public static Integer getValue(String desc) {
        for (RelationshipEnum relationshipEnum : RelationshipEnum.values()) {
            if (StringUtils.equals(relationshipEnum.getDesc(), desc)) {
                return relationshipEnum.getValue();
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

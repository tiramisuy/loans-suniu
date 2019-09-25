package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/10/10.
 */
public class UrgentRecallResultVO implements Serializable {
    private Integer value;
    private String desc;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

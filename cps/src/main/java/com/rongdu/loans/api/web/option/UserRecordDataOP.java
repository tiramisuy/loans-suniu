package com.rongdu.loans.api.web.option;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/21.
 */
public class UserRecordDataOP implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String idNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}

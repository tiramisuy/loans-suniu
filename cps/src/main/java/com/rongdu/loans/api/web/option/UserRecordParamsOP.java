package com.rongdu.loans.api.web.option;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangxiaolong on 2017/8/21.
 */
public class UserRecordParamsOP {
    @NotBlank
    private String tx;
    @NotNull
    private UserRecordDataOP data;

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public UserRecordDataOP getData() {
        return data;
    }

    public void setData(UserRecordDataOP data) {
        this.data = data;
    }
}

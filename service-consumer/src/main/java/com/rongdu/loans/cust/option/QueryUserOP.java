package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class QueryUserOP implements Serializable {


    private static final long serialVersionUID = 7857939581154396378L;

    @NotNull(message = "参数不能为空")
    private String id;

    private String applyId;

    private Boolean snapshot = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Boolean getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Boolean snapshot) {
        this.snapshot = snapshot;
    }
}

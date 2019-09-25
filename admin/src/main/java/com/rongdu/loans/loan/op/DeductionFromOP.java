package com.rongdu.loans.loan.op;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/7/28.
 */
public class DeductionFromOP implements Serializable{

    @NotNull(message = "id不能为空")
    private String id;
    @NotNull(message = "ctx不能为空")
    private String ctx;
    @NotNull(message = "source不能为空")
    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCtx() {
        return ctx;
    }

    public void setCtx(String ctx) {
        this.ctx = ctx;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

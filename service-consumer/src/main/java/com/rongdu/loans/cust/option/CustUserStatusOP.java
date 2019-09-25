package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
public class CustUserStatusOP implements Serializable{
    private static final long serialVersionUID = -8378219540660050575L;
    @NotNull(message = "参数不能为空")
    private String id;//用户id

    @Min(value=0, message="参数不得小于0")
    @Max(value=1, message="参数不得大于1")
    private Integer status;		// 用户状态：1-正常，0-锁定账户

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

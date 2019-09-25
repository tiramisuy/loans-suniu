package com.rongdu.loans.loan.dto;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/7/8.
 */
public class OverdueDTO implements Serializable{
    private static final long serialVersionUID = 256877828448509932L;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 贷款订单号
     */
    private String applyId;
    /**
     * 数量
     */
    private Integer number;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

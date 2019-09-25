package com.rongdu.loans.loan.op;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/9/29.
 */
public class CollectionAssignmentOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;
    /**
     * 拼装的id
     */
    @NotBlank(message = "参数不能为空")
    private String ids;
    /**
     * 催收员id
     */
    @NotBlank(message = "参数不能为空")
    private String operatorId;
    /**
     * 退回时间
     */
    @NotBlank(message = "参数不能为空")
    private String time;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

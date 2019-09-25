package com.rongdu.loans.statistical.enums;

/**
 * Created by zhangxiaolong on 2017/9/11.
 */
public enum SceneStatus {
    NORMAL_STATUS("0","履约"),
    DEFAULT_STATUS("1","违约"),
    FINISH_STATUS("2","结清"),
    GIVE_UP_STATUS("3","用户放弃"),
    NO_PASS_STATUS("4","审批拒绝"),
    PASS_STATUS("5","审批通过"),
    LEND_STATUS("6","放款");

    private String status;
    private String desc;

    SceneStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

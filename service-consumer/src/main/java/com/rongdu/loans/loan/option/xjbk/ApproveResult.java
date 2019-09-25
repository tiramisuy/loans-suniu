package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/8.
 */
@Data
public class ApproveResult implements Serializable {

    private static final long serialVersionUID = -371574394765366945L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("result_type")
    private int resultType;
    @JsonProperty("approve_status")
    private String approveStatus;
    @JsonProperty("approve_time")
    private String approveTime;
    @JsonProperty("approve_amount")
    private String approveAmount;
    @JsonProperty("approve_term")
    private String approveTerm;
    @JsonProperty("term_type")
    private String termType;
    @JsonProperty("approve_remark")
    private String approveRemark;
}

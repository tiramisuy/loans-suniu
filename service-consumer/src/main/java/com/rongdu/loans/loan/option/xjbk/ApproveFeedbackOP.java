package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lee on 2018/5/29.
 */
@Data
public class ApproveFeedbackOP implements Serializable{

    private static final long serialVersionUID = -2737819798360651199L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("approve_status")
    private String approveStatus;
    @JsonProperty("approve_amount")
    private BigDecimal approveAmount;
    @JsonProperty("approve_term")
    private String approveTerm;
    @JsonProperty("term_type")
    private String termType;
    @JsonProperty("approve_remark")
    private String approveRemark;
    @JsonProperty("can_loan_time")
    private String canLoanTime;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("serv_fee")
    private BigDecimal servFee;
    @JsonProperty("apply_id")
    private String applyId;
}

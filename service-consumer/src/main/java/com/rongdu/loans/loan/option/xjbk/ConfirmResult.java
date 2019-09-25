package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/8.
 */
@Data
public class ConfirmResult implements Serializable {

    private static final long serialVersionUID = -7320625998481615604L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("result_type")
    private int resultType;
    @JsonProperty("confirm_status")
    private String confirmStatus;
    @JsonProperty("confirm_time")
    private String confirmTime;
    @JsonProperty("confirm_amount")
    private String confirmAmount;
    @JsonProperty("confirm_term")
    private String confirmTerm;
    @JsonProperty("term_type")
    private String termType;
    @JsonProperty("confirm_remark")
    private String confirmRemark;
}

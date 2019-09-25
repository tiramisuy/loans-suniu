package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/8.
 */
@Data
public class LendingResult implements Serializable {

    private static final long serialVersionUID = 5738721195793822027L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("result_type")
    private int resultType;
    @JsonProperty("lending_status")
    private String lendingStatus;
    @JsonProperty("lending_time")
    private String lendingTime;
    @JsonProperty("lending_amount")
    private String lendingAmount;
    @JsonProperty("lending_term")
    private String lendingTerm;
    @JsonProperty("term_type")
    private String termType;
    @JsonProperty("lending_remark")
    private String lendingRemark;
}

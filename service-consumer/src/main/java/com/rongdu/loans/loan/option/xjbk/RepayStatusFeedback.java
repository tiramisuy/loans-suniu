package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/5/31.
 */
@Data
public class RepayStatusFeedback implements Serializable{
    private static final long serialVersionUID = 4165680169816183270L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("repay_result")
    private String repayResult;
    @JsonProperty("fail_reason")
    private String failReason;
    @JsonProperty("updated_at")
    private String updatedAt;
}

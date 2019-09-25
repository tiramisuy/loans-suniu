package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/5/30.
 */
@Data
public class LendingFeedback implements Serializable{

    private static final long serialVersionUID = -4074297235731301933L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("lending_status")
    private String lendingStatus;
    @JsonProperty("fail_reason")
    private String failReason;
    @JsonProperty("updated_at")
    private String updatedAt;
}

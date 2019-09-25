package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lee on 2018/5/31.
 */
@Data
public class LoanCalculate implements Serializable {

    private static final long serialVersionUID = -4412315909206615522L;
    @JsonProperty("service_fee")
    private BigDecimal serviceFee;
    @JsonProperty("service_fee_desc")
    private String serviceFeeDesc;
    @JsonProperty("interest_fee")
    private BigDecimal interestFee;
    @JsonProperty("receive_amount")
    private BigDecimal receiveAmount;
    @JsonProperty("repay_amount")
    private BigDecimal repayAmount;
    @JsonProperty("repay_plan")
    private List<RepayPlan> repayPlan;
}

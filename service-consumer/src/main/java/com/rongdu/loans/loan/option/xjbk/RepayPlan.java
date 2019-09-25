package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lee on 2018/5/31.
 */
@Data
public class RepayPlan implements Serializable {
    private static final long serialVersionUID = -6814826669484062691L;
    @JsonProperty("repay_amount")
    private BigDecimal repayAmount;
    @JsonProperty("repay_amount_desc")
    private String repayAmountDesc;
    @JsonProperty("period_no")
    private int periodNo;
}

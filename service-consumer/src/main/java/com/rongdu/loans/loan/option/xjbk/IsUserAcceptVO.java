package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2018/6/1.
 */
@Data
public class IsUserAcceptVO implements Serializable {
    private static final long serialVersionUID = -2928945210506996180L;
    private int result;
    private int amount;
    @JsonProperty("min_amount")
    private int minAmount;
    private List<Integer> terms;
    @JsonProperty("term_type")
    private int termType;
    @JsonProperty("loan_mode")
    private int loanMode;
}

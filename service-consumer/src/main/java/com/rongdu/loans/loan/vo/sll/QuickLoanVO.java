package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询复贷和黑名单信息〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class QuickLoanVO implements Serializable {
    private static final long serialVersionUID = -6537845952097553852L;

    @JsonProperty("is_reloan")
    private String isReloan;

    @JsonProperty("amount_type")
    private String amountType;

    @JsonProperty("approval_amount")
    private String approvalAmount;

    @JsonProperty("max_approval_amount")
    private String maxApprovalAmount;

    @JsonProperty("min_approval_amount")
    private String minApprovalAmount;

    @JsonProperty("range_amount")
    private String rangeAmount;

    @JsonProperty("approval_term")
    private String approvalTerm;

    @JsonProperty("term_unit")
    private String termUnit;

    private String reason;
}

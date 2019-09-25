package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送审批结论〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class ApproveFeedbackVO implements Serializable {
    private static final long serialVersionUID = -5175753807614257375L;

    @JsonProperty("order_no")
    private String orderNo;

    private Integer conclusion;

    @JsonProperty("approval_time")
    private String approvalTime;

    @JsonProperty("term_unit")
    private Integer termUnit;

    @JsonProperty("amount_type")
    private Integer amountType;

    @JsonProperty("term_type")
    private Integer termType;

    @JsonProperty("approval_amount")
    private Integer approvalAmount;

    @JsonProperty("approval_term")
    private Integer approvalTerm;

    @JsonProperty("refuse_time")
    private String refuseTime;

    private String remark;
}

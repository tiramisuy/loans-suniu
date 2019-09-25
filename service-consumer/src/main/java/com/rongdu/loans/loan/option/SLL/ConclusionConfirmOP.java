package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送用户确认收款信息〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class ConclusionConfirmOP implements Serializable {
    private static final long serialVersionUID = -2260954918756922913L;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("loan_amount")
    private String loanAmount;

    @JsonProperty("loan_term")
    private int loanTerm;

    @JsonProperty("confirm_return_url")
    private String confirmReturnUrl;
}

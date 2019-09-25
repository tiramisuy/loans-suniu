package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询还款计划〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class RepaymentPlanPullVO implements Serializable {
    private static final long serialVersionUID = 8346373721848460114L;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("open_bank")
    private String openBank;

    @JsonProperty("bank_card")
    private String bankCard;

    @JsonProperty("repayment_plan")
    private List<RepaymentPlanDetail> repaymentPlan;
}

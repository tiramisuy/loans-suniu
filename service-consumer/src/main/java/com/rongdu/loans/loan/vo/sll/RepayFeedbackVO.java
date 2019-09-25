package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送还款状态〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class RepayFeedbackVO implements Serializable {
    private static final long serialVersionUID = -218946483348024631L;

    /**
     * 订单编号
     */
    @JsonProperty("order_no")
    private String orderNo;

    /**
     *还款期数
     */
    @JsonProperty("period_nos")
    private String periodNos;

    /**
     * 本次还款金额，单位元
     */
    @JsonProperty("repay_amount")
    private BigDecimal repayAmount;

    /**
     * 还款状态：1=还款成功 2=还款失败
     */
    @JsonProperty("repay_status")
    private Integer repayStatus;

    /**
     * 还款触发方式：1=主动还款 2=自动代扣
     */
    @JsonProperty("repay_place")
    private Integer repayPlace;

    /**
     * 执行还款时间
     */
    @JsonProperty("success_time")
    private String success_time;

    private String remark;
}

package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询订单状态〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class OrderStatusPullVO implements Serializable {
    private static final long serialVersionUID = -1742152438777656198L;

    /**
     * 订单编号
     */
    @JsonProperty("order_no")
    private String orderNo;

    /**
     * 订单状态
     */
    @JsonProperty("order_status")
    private Integer orderStatus;

    /**
     * 订单状态更新时间
     */
    @JsonProperty("update_time")
    private String updateTime;

    private String remark;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("loan_term")
    private Integer loanTerm;

    @JsonProperty("receive_amount")
    private BigDecimal receiveAmount;

    @JsonProperty("service_fee")
    private BigDecimal serviceFee;

    @JsonProperty("pay_amount")
    private BigDecimal payAmount;

    @JsonProperty("period_amount")
    private String periodAmount;

    @JsonProperty("with_drawals")
    private String withDrawals;
}

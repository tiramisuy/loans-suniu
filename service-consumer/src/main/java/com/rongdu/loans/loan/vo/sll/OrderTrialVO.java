package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈试算接口〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class OrderTrialVO implements Serializable {
    private static final long serialVersionUID = -1815859143116335397L;

    @JsonProperty("receive_amount")
    private BigDecimal receiveAmount;

    @JsonProperty("service_fee")
    private BigDecimal serviceFee;

    @JsonProperty("pay_amount")
    private BigDecimal payAmount;

    @JsonProperty("fee_desc")
    private String feeDesc;

    @JsonProperty("confirm_desc")
    private String confirmDesc;

    @JsonProperty("period_amount")
    private List<BigDecimal> periodAmount;
}

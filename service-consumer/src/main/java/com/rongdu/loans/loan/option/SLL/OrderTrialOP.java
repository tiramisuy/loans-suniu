package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈试算接口〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class OrderTrialOP implements Serializable {
    private static final long serialVersionUID = -3532669867037203878L;

    @JsonProperty("order_no")
    private String orderNo;

    private String amount;

    private String period;
}

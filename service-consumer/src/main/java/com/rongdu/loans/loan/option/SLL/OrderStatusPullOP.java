package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询订单状态〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class OrderStatusPullOP implements Serializable {
    private static final long serialVersionUID = -3658041390438507718L;

    @JsonProperty("order_no")
    private String orderNO;
}

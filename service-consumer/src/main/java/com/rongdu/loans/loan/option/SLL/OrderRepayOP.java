package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送用户还款信息〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class OrderRepayOP implements Serializable {
    private static final long serialVersionUID = 4003114413690540848L;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("period_nos")
    private String periodNos;
}

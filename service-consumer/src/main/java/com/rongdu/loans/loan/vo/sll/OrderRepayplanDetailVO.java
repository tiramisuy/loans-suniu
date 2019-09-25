package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询还款详情〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class OrderRepayplanDetailVO implements Serializable {
    private static final long serialVersionUID = 24685515544621364L;

    @JsonProperty("period_nos")
    private String periodNos;

    private BigDecimal amount;

    @JsonProperty("overdue_amount")
    private BigDecimal overdueAmount;
}

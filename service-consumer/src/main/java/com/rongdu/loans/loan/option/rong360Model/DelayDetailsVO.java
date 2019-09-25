package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-07-05 17:18:13
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class DelayDetailsVO implements Serializable {

    private static final long serialVersionUID = -1565364655699984213L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("defer_option")
    private List<DeferOption> deferOption;
    @JsonProperty("defer_amount_option")
    private List<DeferAmountOption> deferAmountOption;
    @JsonProperty("defer_amount_type")
    private int deferAmountType;
}
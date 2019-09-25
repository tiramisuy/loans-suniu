package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Auto-generated: 2018-07-04 10:52:6
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class CalculationVO implements Serializable {

    private static final long serialVersionUID = -6664685157087194418L;
    @JsonProperty("serviceFee")
    private BigDecimal servicefee;
    @JsonProperty("actual_amount")
    private BigDecimal actualAmount;
    @JsonProperty("repay_amount")
    private BigDecimal repayAmount;
    @JsonProperty("goods_amount")
    private BigDecimal goodsAmount;
    private String remark;


}
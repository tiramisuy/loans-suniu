package com.rongdu.loans.loan.option.rong360Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-07-04 15:10:35
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class ApproveOP implements Serializable {

    private static final long serialVersionUID = -8835837582635705170L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("loan_amount")
    private String loanAmount;
    @JsonProperty("loan_term")
    private int loanTerm;
    @JsonProperty("term_unit")
    private int termUnit;
    private List<GoodsInfo> goodsInfo;

    private String applyId;

}
package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-08 10:9:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class EbusinessExpense implements Serializable {

    private static final long serialVersionUID = -859578519661843718L;
    @JsonProperty("all_category")
    private List<String> allCategory;
    @JsonProperty("all_count")
    private int allCount;
    @JsonProperty("all_amount")
    private float allAmount;
    @JsonProperty("trans_mth")
    private String transMth;

}
package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-05 17:18:13
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class DeferAmountOption implements Serializable{

    private static final long serialVersionUID = -2794238183501448729L;
    @JsonProperty("defer_amount")
    private float deferAmount;
    @JsonProperty("defer_day")
    private int deferDay;
    private String remark;


}
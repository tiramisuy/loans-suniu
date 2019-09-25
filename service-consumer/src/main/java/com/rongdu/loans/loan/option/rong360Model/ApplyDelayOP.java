package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-04 17:45:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ApplyDelayOP implements Serializable{

    private static final long serialVersionUID = -3742295533783492663L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("defer_day")
    private String deferDay;


}
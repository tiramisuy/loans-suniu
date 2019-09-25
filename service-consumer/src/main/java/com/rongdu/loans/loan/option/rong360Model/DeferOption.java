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
public class DeferOption implements Serializable{

    private static final long serialVersionUID = 8580386027762992873L;
    @JsonProperty("after_defer_amount")
    private float afterDeferAmount;
    @JsonProperty("defer_due_time")
    private float deferDueTime;
    @JsonProperty("defer_day")
    private int deferDay;
    private String remark;

}
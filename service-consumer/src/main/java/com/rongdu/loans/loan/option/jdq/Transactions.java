package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-14 10:55:49
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Transactions implements Serializable{

    private static final long serialVersionUID = -6151319962065118791L;
    @JsonProperty("bill_cycle")
    private String billCycle;
    @JsonProperty("cell_phone")
    private String cellPhone;
    @JsonProperty("pay_amt")
    private int payAmt;
    @JsonProperty("plan_amt")
    private int planAmt;
    @JsonProperty("total_amt")
    private int totalAmt;
    @JsonProperty("update_time")
    private String updateTime;

}
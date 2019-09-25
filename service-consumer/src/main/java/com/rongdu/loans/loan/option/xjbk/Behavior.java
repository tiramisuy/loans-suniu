package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Behavior implements Serializable{

    private static final long serialVersionUID = 5906817717994413350L;
    @JsonProperty("sms_cnt")
    private int smsCnt;
    @JsonProperty("cell_phone_num")
    private String cellPhoneNum;
    @JsonProperty("net_flow")
    private int netFlow;
    @JsonProperty("total_amount")
    private double totalAmount;
    @JsonProperty("call_out_time")
    private double callOutTime;
    @JsonProperty("cell_mth")
    private String cellMth;
    @JsonProperty("cell_loc")
    private String cellLoc;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("cell_operator_zh")
    private String cellOperatorZh;
    @JsonProperty("call_out_cnt")
    private int callOutCnt;
    @JsonProperty("cell_operator")
    private String cellOperator;
    @JsonProperty("call_in_time")
    private double callInTime;
    @JsonProperty("call_in_cnt")
    private int callInCnt;

}
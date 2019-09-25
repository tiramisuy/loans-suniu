package com.rongdu.loans.loan.option.dwd.report;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Behavior implements Serializable {

    private static final long serialVersionUID = -2025234919056833780L;
    @JsonProperty("cell_operator_zh")
    private String cellOperatorZh;
    @JsonProperty("net_flow")
    private double netFlow;
    @JsonProperty("call_out_time")
    private double callOutTime;
    @JsonProperty("cell_operator")
    private String cellOperator;
    @JsonProperty("call_in_cnt")
    private int callInCnt;
    @JsonProperty("cell_phone_num")
    private String cellPhoneNum;
    @JsonProperty("sms_cnt")
    private int smsCnt;
    @JsonProperty("cell_loc")
    private String cellLoc;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("total_amount")
    private int totalAmount;
    @JsonProperty("cell_mth")
    private String cellMth;
    @JsonProperty("call_out_cnt")
    private int callOutCnt;
    @JsonProperty("call_in_time")
    private double callInTime;
}
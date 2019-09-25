package com.rongdu.loans.loan.option.SLL;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class Net implements Serializable {

    private static final long serialVersionUID = -4887978631809887120L;
    private int fee;
    @JsonProperty("net_type")
    private String netType;
    @JsonProperty("preferential_fee")
    private String preferentialFee;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("total_time")
    private int totalTime;
    @JsonProperty("total_traffic")
    private String totalTraffic;
    @JsonProperty("trade_addr")
    private String tradeAddr;



}
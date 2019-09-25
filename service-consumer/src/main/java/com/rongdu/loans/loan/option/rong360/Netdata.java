package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@lombok.Data
public class Netdata implements Serializable{

	private static final long serialVersionUID = 4139487728868589637L;
	
	private String fee;
    @JsonProperty("net_type")
    private String netType;
    @JsonProperty("net_way")
    private String netWay;
    @JsonProperty("preferential_fee")
    private String preferentialFee;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("total_time")
    private String totalTime;
    @JsonProperty("total_traffic")
    private String totalTraffic;
    @JsonProperty("trade_addr")
    private String tradeAddr;
}
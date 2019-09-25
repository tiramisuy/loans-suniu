package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-12 10:2:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Netdata implements Serializable{

	private static final long serialVersionUID = 2653991143126247504L;
	
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
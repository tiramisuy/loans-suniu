package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-03 10:38:10
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Teldata implements Serializable{

	private static final long serialVersionUID = -2238405577398488775L;
	
	@JsonProperty("business_name")
    private String businessName;
	@JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("trade_time")
    private long tradeTime;
    @JsonProperty("call_time")
    private String callTime;
    @JsonProperty("trade_addr")
    private String tradeAddr;
    @JsonProperty("receive_phone")
    private String receivePhone;
    @JsonProperty("call_type")
    private String callType;
    private String fee;
    @JsonProperty("special_offer")
    private String specialOffer;
    
    @JsonProperty("call_count")
    private Integer callCount;

}
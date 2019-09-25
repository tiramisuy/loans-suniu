package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.Data;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Teldata implements Serializable{

	private static final long serialVersionUID = -900102447487819781L;
	
	@JsonProperty("business_name")
    private String businessName;
    @JsonProperty("call_time")
    private String callTime;
    @JsonProperty("call_type")
    private String callType;
    private String fee;
    @JsonProperty("special_offer")
    private String specialOffer;
    @JsonProperty("trade_addr")
    private String tradeAddr;
    @JsonProperty("trade_time")
    private String tradeTime;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("receive_phone")
    private String receivePhone;


}
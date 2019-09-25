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
public class Msgdata implements Serializable{

	private static final long serialVersionUID = 754162139249996588L;
	
	@JsonProperty("send_time")
    private String sendTime;
    @JsonProperty("trade_way")
    private String tradeWay;
    @JsonProperty("receiver_phone")
    private String receiverPhone;

}
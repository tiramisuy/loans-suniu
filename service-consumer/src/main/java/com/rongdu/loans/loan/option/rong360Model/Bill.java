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
public class Bill implements Serializable{

	private static final long serialVersionUID = 2630094553419000209L;
	
	private String month;
    @JsonProperty("call_pay")
    private String callPay;
    @JsonProperty("package_fee")
    private String packageFee;
    @JsonProperty("msg_fee")
    private String msgFee;
    @JsonProperty("tel_fee")
    private String telFee;
    @JsonProperty("net_fee")
    private String netFee;
    @JsonProperty("addtional_fee")
    private String addtionalFee;
    @JsonProperty("preferential_fee")
    private String preferentialFee;
    @JsonProperty("generation_fee")
    private String generationFee;
    @JsonProperty("other_fee")
    private String otherFee;
    private String score;
    private String url;
    @JsonProperty("otherspaid_fee")
    private String otherspaidFee;
    @JsonProperty("pay_fee")
    private String payFee;
}
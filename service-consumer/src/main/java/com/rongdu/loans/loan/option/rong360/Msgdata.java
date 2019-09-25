package com.rongdu.loans.loan.option.rong360;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@lombok.Data
public class Msgdata implements Serializable {

    private static final long serialVersionUID = -7436796894704264558L;

    @JsonProperty("business_name")
    private String businessName;
    private String fee;
    @JsonProperty("send_time")
    private String sendTime;
    @JsonProperty("trade_addr")
    private String tradeAddr;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("trade_way")
    private int tradeWay;
    @JsonProperty("receiver_phone")
    private String receiverPhone;

}
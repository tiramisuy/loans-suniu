package com.rongdu.loans.loan.option.SLL;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */

public class Teldata  implements Serializable{

    private static final long serialVersionUID = -2923118070665283019L;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("trade_time")
    private int tradeTime;
    @JsonProperty("call_time")
    private String callTime;
    @JsonProperty("trade_addr")
    private String tradeAddr;
    @JsonProperty("receive_phone")
    private String receivePhone;
    @JsonProperty("call_type")
    private String callType;
    private int fee;
    public void setTradeType(String tradeType) {
         this.tradeType = tradeType;
     }
     public String getTradeType() {
         return tradeType;
     }

    public void setTradeTime(int tradeTime) {
         this.tradeTime = tradeTime;
     }
     public int getTradeTime() {
         return tradeTime;
     }

    public void setCallTime(String callTime) {
         this.callTime = callTime;
     }
     public String getCallTime() {
         return callTime;
     }

    public void setTradeAddr(String tradeAddr) {
         this.tradeAddr = tradeAddr;
     }
     public String getTradeAddr() {
         return tradeAddr;
     }

    public void setReceivePhone(String receivePhone) {
         this.receivePhone = receivePhone;
     }
     public String getReceivePhone() {
         return receivePhone;
     }

    public void setCallType(String callType) {
         this.callType = callType;
     }
     public String getCallType() {
         return callType;
     }

    public void setFee(int fee) {
         this.fee = fee;
     }
     public int getFee() {
         return fee;
     }

}
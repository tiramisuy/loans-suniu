package com.rongdu.loans.loan.option.SLL;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Msgdata implements Serializable {

    private static final long serialVersionUID = -4019497650702697725L;
    @JsonProperty("send_time")
    private String sendTime;
    @JsonProperty("trade_way")
    private String tradeWay;
    @JsonProperty("receiver_phone")
    private String receiverPhone;
    @JsonProperty("business_name")
    private String businessName;
    private int fee;
    @JsonProperty("trade_addr")
    private String tradeAddr;
    @JsonProperty("trade_type")
    private String tradeType;
    public void setSendTime(String sendTime) {
         this.sendTime = sendTime;
     }
     public String getSendTime() {
         return sendTime;
     }

    public void setTradeWay(String tradeWay) {
         this.tradeWay = tradeWay;
     }
     public String getTradeWay() {
         return tradeWay;
     }

    public void setReceiverPhone(String receiverPhone) {
         this.receiverPhone = receiverPhone;
     }
     public String getReceiverPhone() {
         return receiverPhone;
     }

    public void setBusinessName(String businessName) {
         this.businessName = businessName;
     }
     public String getBusinessName() {
         return businessName;
     }

    public void setFee(int fee) {
         this.fee = fee;
     }
     public int getFee() {
         return fee;
     }

    public void setTradeAddr(String tradeAddr) {
         this.tradeAddr = tradeAddr;
     }
     public String getTradeAddr() {
         return tradeAddr;
     }

    public void setTradeType(String tradeType) {
         this.tradeType = tradeType;
     }
     public String getTradeType() {
         return tradeType;
     }

}
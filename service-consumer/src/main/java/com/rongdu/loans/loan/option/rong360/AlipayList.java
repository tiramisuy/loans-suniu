package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class AlipayList implements Serializable{

	private static final long serialVersionUID = -5458672441470154031L;
	
	@JsonProperty("trade_no")
    private String tradeNo;
    @JsonProperty("pay_time")
    private String payTime;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("trade_no_type")
    private String tradeNoType;
    @JsonProperty("receiver_name")
    private String receiverName;
    private String amount;
    private String status;
    private String source;
    @JsonProperty("trade_classification")
    private String tradeClassification;
    @JsonProperty("alipay_name")
    private String alipayName;
    public void setTradeNo(String tradeNo) {
         this.tradeNo = tradeNo;
     }
     public String getTradeNo() {
         return tradeNo;
     }

    public void setPayTime(String payTime) {
         this.payTime = payTime;
     }
     public String getPayTime() {
         return payTime;
     }

    public void setTradeType(String tradeType) {
         this.tradeType = tradeType;
     }
     public String getTradeType() {
         return tradeType;
     }

    public void setTradeNoType(String tradeNoType) {
         this.tradeNoType = tradeNoType;
     }
     public String getTradeNoType() {
         return tradeNoType;
     }

    public void setReceiverName(String receiverName) {
         this.receiverName = receiverName;
     }
     public String getReceiverName() {
         return receiverName;
     }

    public void setAmount(String amount) {
         this.amount = amount;
     }
     public String getAmount() {
         return amount;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setTradeClassification(String tradeClassification) {
         this.tradeClassification = tradeClassification;
     }
     public String getTradeClassification() {
         return tradeClassification;
     }

    public void setAlipayName(String alipayName) {
         this.alipayName = alipayName;
     }
     public String getAlipayName() {
         return alipayName;
     }

}
package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Detail implements Serializable{

	private static final long serialVersionUID = 2647286318192098396L;
	
	private String no;
    @JsonProperty("trade_time")
    private String tradeTime;
    private String payment;
    private String deposit;
    @JsonProperty("abstract")
    private String abstractT;
    private String balance;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("trade_country")
    private String tradeCountry;
    @JsonProperty("trade_place")
    private String tradePlace;
    @JsonProperty("other_account")
    private String otherAccount;
    @JsonProperty("other_account_name")
    private String otherAccountName;
    @JsonProperty("other_account_bank")
    private String otherAccountBank;
    public void setNo(String no) {
         this.no = no;
     }
     public String getNo() {
         return no;
     }

    public void setTradeTime(String tradeTime) {
         this.tradeTime = tradeTime;
     }
     public String getTradeTime() {
         return tradeTime;
     }

    public void setPayment(String payment) {
         this.payment = payment;
     }
     public String getPayment() {
         return payment;
     }

    public void setDeposit(String deposit) {
         this.deposit = deposit;
     }
     public String getDeposit() {
         return deposit;
     }

    public void setAbstractT(String abstractT) {
         this.abstractT = abstractT;
     }
     public String getAbstractT() {
         return abstractT;
     }

    public void setBalance(String balance) {
         this.balance = balance;
     }
     public String getBalance() {
         return balance;
     }

    public void setTradeType(String tradeType) {
         this.tradeType = tradeType;
     }
     public String getTradeType() {
         return tradeType;
     }

    public void setTradeCountry(String tradeCountry) {
         this.tradeCountry = tradeCountry;
     }
     public String getTradeCountry() {
         return tradeCountry;
     }

    public void setTradePlace(String tradePlace) {
         this.tradePlace = tradePlace;
     }
     public String getTradePlace() {
         return tradePlace;
     }

    public void setOtherAccount(String otherAccount) {
         this.otherAccount = otherAccount;
     }
     public String getOtherAccount() {
         return otherAccount;
     }

    public void setOtherAccountName(String otherAccountName) {
         this.otherAccountName = otherAccountName;
     }
     public String getOtherAccountName() {
         return otherAccountName;
     }

    public void setOtherAccountBank(String otherAccountBank) {
         this.otherAccountBank = otherAccountBank;
     }
     public String getOtherAccountBank() {
         return otherAccountBank;
     }

}
package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Bankcards implements Serializable{

	private static final long serialVersionUID = 9024908560467074078L;
	
	@JsonProperty("login_name")
    private String loginName;
    @JsonProperty("card_id")
    private String cardId;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("tail_number")
    private String tailNumber;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("owner_name")
    private String ownerName;
    private String phone;
    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

    public void setCardId(String cardId) {
         this.cardId = cardId;
     }
     public String getCardId() {
         return cardId;
     }

    public void setBankName(String bankName) {
         this.bankName = bankName;
     }
     public String getBankName() {
         return bankName;
     }

    public void setTailNumber(String tailNumber) {
         this.tailNumber = tailNumber;
     }
     public String getTailNumber() {
         return tailNumber;
     }

    public void setCardType(String cardType) {
         this.cardType = cardType;
     }
     public String getCardType() {
         return cardType;
     }

    public void setOwnerName(String ownerName) {
         this.ownerName = ownerName;
     }
     public String getOwnerName() {
         return ownerName;
     }

    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

}
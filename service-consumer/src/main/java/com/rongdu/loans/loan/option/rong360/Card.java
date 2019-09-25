package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Card implements Serializable{

	private static final long serialVersionUID = 7808553072946658219L;
	
	private int bank;
    private String name;
    @JsonProperty("payment_due_date")
    private Date paymentDueDate;
    @JsonProperty("credit_card")
    private String creditCard;
    private String type;
    @JsonProperty("login_account")
    private String loginAccount;
    @JsonProperty("card_no")
    private String cardNo;
    public void setBank(int bank) {
         this.bank = bank;
     }
     public int getBank() {
         return bank;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPaymentDueDate(Date paymentDueDate) {
         this.paymentDueDate = paymentDueDate;
     }
     public Date getPaymentDueDate() {
         return paymentDueDate;
     }

    public void setCreditCard(String creditCard) {
         this.creditCard = creditCard;
     }
     public String getCreditCard() {
         return creditCard;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setLoginAccount(String loginAccount) {
         this.loginAccount = loginAccount;
     }
     public String getLoginAccount() {
         return loginAccount;
     }

    public void setCardNo(String cardNo) {
         this.cardNo = cardNo;
     }
     public String getCardNo() {
         return cardNo;
     }

}
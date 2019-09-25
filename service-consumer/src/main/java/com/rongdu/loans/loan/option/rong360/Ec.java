package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Ec implements Serializable{
	
	private static final long serialVersionUID = 339030112502441636L;
	
	private String mail;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("card_no")
    private String cardNo;
    private String name;
    private String sex;
    @JsonProperty("last_balance")
    private String lastBalance;
    @JsonProperty("last_payment")
    private String lastPayment;
    @JsonProperty("statement_start_date")
    private String statementStartDate;
    @JsonProperty("statement_end_date")
    private String statementEndDate;
    @JsonProperty("payment_cur_date")
    private String paymentCurDate;
    @JsonProperty("payment_due_date")
    private String paymentDueDate;
    @JsonProperty("credit_limit")
    private String creditLimit;
    @JsonProperty("total_points")
    private String totalPoints;
    @JsonProperty("new_balance")
    private String newBalance;
    @JsonProperty("min_payment")
    private String minPayment;
    @JsonProperty("new_charges")
    private String newCharges;
    private String adjustment;
    private String interest;
    @JsonProperty("last_points")
    private String lastPoints;
    @JsonProperty("earned_points")
    private String earnedPoints;
    @JsonProperty("sender_email")
    private String senderEmail;
    @JsonProperty("adjusted_points")
    private String adjustedPoints;
    @JsonProperty("available_balance_usd")
    private String availableBalanceUsd;
    @JsonProperty("available_balance")
    private String availableBalance;
    @JsonProperty("cash_advance_limit_usd")
    private String cashAdvanceLimitUsd;
    @JsonProperty("credit_limit_usd")
    private String creditLimitUsd;
    @JsonProperty("cash_advance_limit")
    private String cashAdvanceLimit;
    @JsonProperty("min_payment_usd")
    private String minPaymentUsd;
    @JsonProperty("new_balance_usd")
    private String newBalanceUsd;
    @JsonProperty("redeemed_points")
    private String redeemedPoints;
    @JsonProperty("rewarded_points")
    private String rewardedPoints;
    private String time;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("trans_detail")
    private List<TransDetail> transDetail;
    @JsonProperty("is_simple")
    private int isSimple;
    private String url;
    public void setMail(String mail) {
         this.mail = mail;
     }
     public String getMail() {
         return mail;
     }

    public void setBankName(String bankName) {
         this.bankName = bankName;
     }
     public String getBankName() {
         return bankName;
     }

    public void setCardNo(String cardNo) {
         this.cardNo = cardNo;
     }
     public String getCardNo() {
         return cardNo;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setSex(String sex) {
         this.sex = sex;
     }
     public String getSex() {
         return sex;
     }

    public void setLastBalance(String lastBalance) {
         this.lastBalance = lastBalance;
     }
     public String getLastBalance() {
         return lastBalance;
     }

    public void setLastPayment(String lastPayment) {
         this.lastPayment = lastPayment;
     }
     public String getLastPayment() {
         return lastPayment;
     }

    public void setStatementStartDate(String statementStartDate) {
         this.statementStartDate = statementStartDate;
     }
     public String getStatementStartDate() {
         return statementStartDate;
     }

    public void setStatementEndDate(String statementEndDate) {
         this.statementEndDate = statementEndDate;
     }
     public String getStatementEndDate() {
         return statementEndDate;
     }

    public void setPaymentCurDate(String paymentCurDate) {
         this.paymentCurDate = paymentCurDate;
     }
     public String getPaymentCurDate() {
         return paymentCurDate;
     }

    public void setPaymentDueDate(String paymentDueDate) {
         this.paymentDueDate = paymentDueDate;
     }
     public String getPaymentDueDate() {
         return paymentDueDate;
     }

    public void setCreditLimit(String creditLimit) {
         this.creditLimit = creditLimit;
     }
     public String getCreditLimit() {
         return creditLimit;
     }

    public void setTotalPoints(String totalPoints) {
         this.totalPoints = totalPoints;
     }
     public String getTotalPoints() {
         return totalPoints;
     }

    public void setNewBalance(String newBalance) {
         this.newBalance = newBalance;
     }
     public String getNewBalance() {
         return newBalance;
     }

    public void setMinPayment(String minPayment) {
         this.minPayment = minPayment;
     }
     public String getMinPayment() {
         return minPayment;
     }

    public void setNewCharges(String newCharges) {
         this.newCharges = newCharges;
     }
     public String getNewCharges() {
         return newCharges;
     }

    public void setAdjustment(String adjustment) {
         this.adjustment = adjustment;
     }
     public String getAdjustment() {
         return adjustment;
     }

    public void setInterest(String interest) {
         this.interest = interest;
     }
     public String getInterest() {
         return interest;
     }

    public void setLastPoints(String lastPoints) {
         this.lastPoints = lastPoints;
     }
     public String getLastPoints() {
         return lastPoints;
     }

    public void setEarnedPoints(String earnedPoints) {
         this.earnedPoints = earnedPoints;
     }
     public String getEarnedPoints() {
         return earnedPoints;
     }

    public void setSenderEmail(String senderEmail) {
         this.senderEmail = senderEmail;
     }
     public String getSenderEmail() {
         return senderEmail;
     }

    public void setAdjustedPoints(String adjustedPoints) {
         this.adjustedPoints = adjustedPoints;
     }
     public String getAdjustedPoints() {
         return adjustedPoints;
     }

    public void setAvailableBalanceUsd(String availableBalanceUsd) {
         this.availableBalanceUsd = availableBalanceUsd;
     }
     public String getAvailableBalanceUsd() {
         return availableBalanceUsd;
     }

    public void setAvailableBalance(String availableBalance) {
         this.availableBalance = availableBalance;
     }
     public String getAvailableBalance() {
         return availableBalance;
     }

    public void setCashAdvanceLimitUsd(String cashAdvanceLimitUsd) {
         this.cashAdvanceLimitUsd = cashAdvanceLimitUsd;
     }
     public String getCashAdvanceLimitUsd() {
         return cashAdvanceLimitUsd;
     }

    public void setCreditLimitUsd(String creditLimitUsd) {
         this.creditLimitUsd = creditLimitUsd;
     }
     public String getCreditLimitUsd() {
         return creditLimitUsd;
     }

    public void setCashAdvanceLimit(String cashAdvanceLimit) {
         this.cashAdvanceLimit = cashAdvanceLimit;
     }
     public String getCashAdvanceLimit() {
         return cashAdvanceLimit;
     }

    public void setMinPaymentUsd(String minPaymentUsd) {
         this.minPaymentUsd = minPaymentUsd;
     }
     public String getMinPaymentUsd() {
         return minPaymentUsd;
     }

    public void setNewBalanceUsd(String newBalanceUsd) {
         this.newBalanceUsd = newBalanceUsd;
     }
     public String getNewBalanceUsd() {
         return newBalanceUsd;
     }

    public void setRedeemedPoints(String redeemedPoints) {
         this.redeemedPoints = redeemedPoints;
     }
     public String getRedeemedPoints() {
         return redeemedPoints;
     }

    public void setRewardedPoints(String rewardedPoints) {
         this.rewardedPoints = rewardedPoints;
     }
     public String getRewardedPoints() {
         return rewardedPoints;
     }

    public void setTime(String time) {
         this.time = time;
     }
     public String getTime() {
         return time;
     }

    public void setCreateTime(String createTime) {
         this.createTime = createTime;
     }
     public String getCreateTime() {
         return createTime;
     }

    public void setTransDetail(List<TransDetail> transDetail) {
         this.transDetail = transDetail;
     }
     public List<TransDetail> getTransDetail() {
         return transDetail;
     }

    public void setIsSimple(int isSimple) {
         this.isSimple = isSimple;
     }
     public int getIsSimple() {
         return isSimple;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}
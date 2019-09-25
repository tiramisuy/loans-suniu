package com.rongdu.loans.loan.option.SLL;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Orderinfo implements Serializable {

    private static final long serialVersionUID = 4710876679396049378L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_mobile")
    private String userMobile;
    @JsonProperty("application_amount")
    private String applicationAmount;
    @JsonProperty("application_term")
    private int applicationTerm;
    @JsonProperty("term_unit")
    private int termUnit;
    @JsonProperty("order_time")
    private int orderTime;
    private int status;
    private String bank;
    private String product;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("is_reloan")
    private int isReloan;
    public void setOrderNo(String orderNo) {
         this.orderNo = orderNo;
     }
     public String getOrderNo() {
         return orderNo;
     }

    public void setUserName(String userName) {
         this.userName = userName;
     }
     public String getUserName() {
         return userName;
     }

    public void setUserMobile(String userMobile) {
         this.userMobile = userMobile;
     }
     public String getUserMobile() {
         return userMobile;
     }

    public void setApplicationAmount(String applicationAmount) {
         this.applicationAmount = applicationAmount;
     }
     public String getApplicationAmount() {
         return applicationAmount;
     }

    public void setApplicationTerm(int applicationTerm) {
         this.applicationTerm = applicationTerm;
     }
     public int getApplicationTerm() {
         return applicationTerm;
     }

    public void setTermUnit(int termUnit) {
         this.termUnit = termUnit;
     }
     public int getTermUnit() {
         return termUnit;
     }

    public void setOrderTime(int orderTime) {
         this.orderTime = orderTime;
     }
     public int getOrderTime() {
         return orderTime;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setBank(String bank) {
         this.bank = bank;
     }
     public String getBank() {
         return bank;
     }

    public void setProduct(String product) {
         this.product = product;
     }
     public String getProduct() {
         return product;
     }

    public void setProductId(String productId) {
         this.productId = productId;
     }
     public String getProductId() {
         return productId;
     }

    public void setIsReloan(int isReloan) {
         this.isReloan = isReloan;
     }
     public int getIsReloan() {
         return isReloan;
     }

}
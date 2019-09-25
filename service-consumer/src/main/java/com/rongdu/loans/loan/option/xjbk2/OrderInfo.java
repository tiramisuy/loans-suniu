package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class OrderInfo {

    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("loan_amount")
    private String loanAmount;
    @JsonProperty("loan_term")
    private String loanTerm;
    @JsonProperty("term_type")
    private String termType;
    public void setOrderSn(String orderSn) {
         this.orderSn = orderSn;
     }
     public String getOrderSn() {
         return orderSn;
     }

    public void setLoanAmount(String loanAmount) {
         this.loanAmount = loanAmount;
     }
     public String getLoanAmount() {
         return loanAmount;
     }

    public void setLoanTerm(String loanTerm) {
         this.loanTerm = loanTerm;
     }
     public String getLoanTerm() {
         return loanTerm;
     }

    public void setTermType(String termType) {
         this.termType = termType;
     }
     public String getTermType() {
         return termType;
     }

}
package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = -6398120886865163888L;
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
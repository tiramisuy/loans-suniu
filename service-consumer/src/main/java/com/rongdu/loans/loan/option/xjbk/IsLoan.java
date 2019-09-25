package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class IsLoan implements Serializable{

    private static final long serialVersionUID = -7423116281239936441L;
    @JsonProperty("is_loan")
    private String isLoan;
    @JsonProperty("loan_type")
    private String loanType;
    public void setIsLoan(String isLoan) {
         this.isLoan = isLoan;
     }
     public String getIsLoan() {
         return isLoan;
     }

    public void setLoanType(String loanType) {
         this.loanType = loanType;
     }
     public String getLoanType() {
         return loanType;
     }

}
package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class LoanUse implements Serializable {


    private static final long serialVersionUID = 6942495782483100347L;
    @JsonProperty("loan_use")
    private String loanUse;
    public void setLoanUse(String loanUse) {
         this.loanUse = loanUse;
     }
     public String getLoanUse() {
         return loanUse;
     }

}
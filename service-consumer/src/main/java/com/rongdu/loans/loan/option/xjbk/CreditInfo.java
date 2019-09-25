package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class CreditInfo implements Serializable{

    private static final long serialVersionUID = 2616566392590557990L;
    @JsonProperty("credit_status")
    private String creditStatus;
    public void setCreditStatus(String creditStatus) {
         this.creditStatus = creditStatus;
     }
     public String getCreditStatus() {
         return creditStatus;
     }

}
package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class WorkFreeInfo implements Serializable{

    private static final long serialVersionUID = 1576624059081127847L;
    @JsonProperty("free_income")
    private String freeIncome;
    public void setFreeIncome(String freeIncome) {
         this.freeIncome = freeIncome;
     }
     public String getFreeIncome() {
         return freeIncome;
     }

}
package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class BusinessType implements Serializable{

    private static final long serialVersionUID = 2192338269171576873L;
    @JsonProperty("business_type")
    private String businessType;
    public void setBusinessType(String businessType) {
         this.businessType = businessType;
     }
     public String getBusinessType() {
         return businessType;
     }

}
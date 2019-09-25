package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class HousingSecurity implements Serializable{

    private static final long serialVersionUID = 2634599370166626431L;
    @JsonProperty("housing_fund_month")
    private String housingFundMonth;
    public void setHousingFundMonth(String housingFundMonth) {
         this.housingFundMonth = housingFundMonth;
     }
     public String getHousingFundMonth() {
         return housingFundMonth;
     }

}
package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class RevenueSource implements Serializable{

    private static final long serialVersionUID = -3552514881934582575L;
    @JsonProperty("revenue_source")
    private String revenueSource;
    public void setRevenueSource(String revenueSource) {
         this.revenueSource = revenueSource;
     }
     public String getRevenueSource() {
         return revenueSource;
     }

}
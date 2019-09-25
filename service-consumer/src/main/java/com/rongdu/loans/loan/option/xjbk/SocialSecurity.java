package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class SocialSecurity implements Serializable{

    private static final long serialVersionUID = -2856454970291403546L;
    @JsonProperty("social_ins_month")
    private String socialInsMonth;
    public void setSocialInsMonth(String socialInsMonth) {
         this.socialInsMonth = socialInsMonth;
     }
     public String getSocialInsMonth() {
         return socialInsMonth;
     }

}
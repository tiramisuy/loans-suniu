package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ProfessionType implements Serializable{

    private static final long serialVersionUID = 7657547761355451795L;
    @JsonProperty("profession_type")
    private String professionType;
    public void setProfessionType(String professionType) {
         this.professionType = professionType;
     }
     public String getProfessionType() {
         return professionType;
     }

}
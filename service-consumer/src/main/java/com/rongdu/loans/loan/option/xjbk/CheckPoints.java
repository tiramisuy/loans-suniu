package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class CheckPoints implements Serializable{

    private static final long serialVersionUID = 6755652089373479869L;
    @JsonProperty("key_value")
    private String keyValue;
    public void setKeyValue(String keyValue) {
         this.keyValue = keyValue;
     }
     public String getKeyValue() {
         return keyValue;
     }

}
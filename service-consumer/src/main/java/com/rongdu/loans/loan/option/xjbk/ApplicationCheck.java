package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ApplicationCheck implements Serializable{

    private static final long serialVersionUID = 9208748621430500419L;
    @JsonProperty("app_point")
    private String appPoint;
    @JsonProperty("check_points")
    private CheckPoints checkPoints;
    public void setAppPoint(String appPoint) {
         this.appPoint = appPoint;
     }
     public String getAppPoint() {
         return appPoint;
     }

    public void setCheckPoints(CheckPoints checkPoints) {
         this.checkPoints = checkPoints;
     }
     public CheckPoints getCheckPoints() {
         return checkPoints;
     }

}
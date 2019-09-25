package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ApplicationCheck implements Serializable {

    private static final long serialVersionUID = -2692351946016671136L;
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
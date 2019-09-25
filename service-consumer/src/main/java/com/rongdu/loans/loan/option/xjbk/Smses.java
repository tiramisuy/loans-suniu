package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Smses implements Serializable{

    private static final long serialVersionUID = -800858858858160484L;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("update_time")
    private String updateTime;
    private double subtotal;
    private String place;
    @JsonProperty("init_type")
    private String initType;
    @JsonProperty("other_cell_phone")
    private String otherCellPhone;
    @JsonProperty("cell_phone")
    private String cellPhone;
    public void setStartTime(String startTime) {
         this.startTime = startTime;
     }
     public String getStartTime() {
         return startTime;
     }

    public void setUpdateTime(String updateTime) {
         this.updateTime = updateTime;
     }
     public String getUpdateTime() {
         return updateTime;
     }

    public void setSubtotal(double subtotal) {
         this.subtotal = subtotal;
     }
     public double getSubtotal() {
         return subtotal;
     }

    public void setPlace(String place) {
         this.place = place;
     }
     public String getPlace() {
         return place;
     }

    public void setInitType(String initType) {
         this.initType = initType;
     }
     public String getInitType() {
         return initType;
     }

    public void setOtherCellPhone(String otherCellPhone) {
         this.otherCellPhone = otherCellPhone;
     }
     public String getOtherCellPhone() {
         return otherCellPhone;
     }

    public void setCellPhone(String cellPhone) {
         this.cellPhone = cellPhone;
     }
     public String getCellPhone() {
         return cellPhone;
     }

}
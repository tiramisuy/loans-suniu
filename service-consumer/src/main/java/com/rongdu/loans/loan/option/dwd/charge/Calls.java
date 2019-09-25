package com.rongdu.loans.loan.option.dwd.charge;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-11-01 15:58:48
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Calls implements Serializable{

    private static final long serialVersionUID = 2911218454550411830L;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("use_time")
    private int useTime;
    private int subtotal;
    private String place;
    @JsonProperty("init_type")
    private String initType;
    @JsonProperty("call_type")
    private String callType;
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

    public void setUseTime(int useTime) {
         this.useTime = useTime;
     }
     public int getUseTime() {
         return useTime;
     }

    public void setSubtotal(int subtotal) {
         this.subtotal = subtotal;
     }
     public int getSubtotal() {
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

    public void setCallType(String callType) {
         this.callType = callType;
     }
     public String getCallType() {
         return callType;
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
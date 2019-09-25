package com.rongdu.loans.loan.option.dwd.charge;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-11-01 15:58:48
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Nets implements Serializable{

    private static final long serialVersionUID = 8364692815385771433L;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("use_time")
    private int useTime;
    private int subflow;
    @JsonProperty("net_type")
    private String netType;
    private int subtotal;
    private String place;
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

    public void setSubflow(int subflow) {
         this.subflow = subflow;
     }
     public int getSubflow() {
         return subflow;
     }

    public void setNetType(String netType) {
         this.netType = netType;
     }
     public String getNetType() {
         return netType;
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

    public void setCellPhone(String cellPhone) {
         this.cellPhone = cellPhone;
     }
     public String getCellPhone() {
         return cellPhone;
     }

}
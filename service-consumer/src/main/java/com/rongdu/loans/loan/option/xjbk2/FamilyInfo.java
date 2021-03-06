package com.rongdu.loans.loan.option.xjbk2;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class FamilyInfo implements Serializable {

    private static final long serialVersionUID = 8007061943257217383L;
    @JsonProperty("home_areas")
    private String homeAreas;
    @JsonProperty("home_address")
    private String homeAddress;
    @JsonProperty("live_time")
    private String liveTime;
    @JsonProperty("house_type")
    private String houseType;
    @JsonProperty("permanent_areas")
    private String permanentAreas;
    public void setHomeAreas(String homeAreas) {
         this.homeAreas = homeAreas;
     }
     public String getHomeAreas() {
         return homeAreas;
     }

    public void setHomeAddress(String homeAddress) {
         this.homeAddress = homeAddress;
     }
     public String getHomeAddress() {
         return homeAddress;
     }

    public void setLiveTime(String liveTime) {
         this.liveTime = liveTime;
     }
     public String getLiveTime() {
         return liveTime;
     }

    public void setHouseType(String houseType) {
         this.houseType = houseType;
     }
     public String getHouseType() {
         return houseType;
     }

    public void setPermanentAreas(String permanentAreas) {
         this.permanentAreas = permanentAreas;
     }
     public String getPermanentAreas() {
         return permanentAreas;
     }

}
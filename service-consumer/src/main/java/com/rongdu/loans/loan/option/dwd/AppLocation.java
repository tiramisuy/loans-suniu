package com.rongdu.loans.loan.option.dwd;

import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-30 16:11:56
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class AppLocation implements Serializable{

    private static final long serialVersionUID = 6947094784628672490L;
    private String address;
    private String lat;
    private String lon;
    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setLat(String lat) {
         this.lat = lat;
     }
     public String getLat() {
         return lat;
     }

    public void setLon(String lon) {
         this.lon = lon;
     }
     public String getLon() {
         return lon;
     }

}
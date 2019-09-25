package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

/**
 * Auto-generated: 2018-06-29 11:7:0
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class AppLocation implements Serializable{

	private static final long serialVersionUID = 8663353225353951167L;
	
	private String lat;
    private String lon;
    private String address;
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

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

}
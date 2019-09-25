package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class HouseInfo implements Serializable{

    private static final long serialVersionUID = -3149465782293085412L;
    @JsonProperty("house_status")
    private String houseStatus;
    private String location;
    @JsonProperty("house_price")
    private String housePrice;
    @JsonProperty("house_loan_status")
    private String houseLoanStatus;
    @JsonProperty("house_pledge_status")
    private String housePledgeStatus;
    public void setHouseStatus(String houseStatus) {
         this.houseStatus = houseStatus;
     }
     public String getHouseStatus() {
         return houseStatus;
     }

    public void setLocation(String location) {
         this.location = location;
     }
     public String getLocation() {
         return location;
     }

    public void setHousePrice(String housePrice) {
         this.housePrice = housePrice;
     }
     public String getHousePrice() {
         return housePrice;
     }

    public void setHouseLoanStatus(String houseLoanStatus) {
         this.houseLoanStatus = houseLoanStatus;
     }
     public String getHouseLoanStatus() {
         return houseLoanStatus;
     }

    public void setHousePledgeStatus(String housePledgeStatus) {
         this.housePledgeStatus = housePledgeStatus;
     }
     public String getHousePledgeStatus() {
         return housePledgeStatus;
     }

}
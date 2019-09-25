package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class CarInfo implements Serializable{

    private static final long serialVersionUID = 3059273842590156886L;
    @JsonProperty("car_status")
    private String carStatus;
    @JsonProperty("car_price")
    private String carPrice;
    @JsonProperty("car_life_time")
    private String carLifeTime;
    public void setCarStatus(String carStatus) {
         this.carStatus = carStatus;
     }
     public String getCarStatus() {
         return carStatus;
     }

    public void setCarPrice(String carPrice) {
         this.carPrice = carPrice;
     }
     public String getCarPrice() {
         return carPrice;
     }

    public void setCarLifeTime(String carLifeTime) {
         this.carLifeTime = carLifeTime;
     }
     public String getCarLifeTime() {
         return carLifeTime;
     }

}
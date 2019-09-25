package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Recharge implements Serializable{

	private static final long serialVersionUID = 9213777460612593676L;
	
	private String fee;
    @JsonProperty("recharge_time")
    private String rechargeTime;
    @JsonProperty("recharge_way")
    private String rechargeWay;
    public void setFee(String fee) {
         this.fee = fee;
     }
     public String getFee() {
         return fee;
     }

    public void setRechargeTime(String rechargeTime) {
         this.rechargeTime = rechargeTime;
     }
     public String getRechargeTime() {
         return rechargeTime;
     }

    public void setRechargeWay(String rechargeWay) {
         this.rechargeWay = rechargeWay;
     }
     public String getRechargeWay() {
         return rechargeWay;
     }

}
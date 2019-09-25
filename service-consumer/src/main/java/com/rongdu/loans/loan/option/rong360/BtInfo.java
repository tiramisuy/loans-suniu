package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class BtInfo implements Serializable{

	private static final long serialVersionUID = -7839475657470435929L;
	
	@JsonProperty("login_name")
    private String loginName;
    @JsonProperty("bt_credit_point")
    private double btCreditPoint;
    @JsonProperty("bt_overdraft")
    private double btOverdraft;
    @JsonProperty("bt_quota")
    private int btQuota;
    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

    public void setBtCreditPoint(double btCreditPoint) {
         this.btCreditPoint = btCreditPoint;
     }
     public double getBtCreditPoint() {
         return btCreditPoint;
     }

    public void setBtOverdraft(double btOverdraft) {
         this.btOverdraft = btOverdraft;
     }
     public double getBtOverdraft() {
         return btOverdraft;
     }

    public void setBtQuota(int btQuota) {
         this.btQuota = btQuota;
     }
     public int getBtQuota() {
         return btQuota;
     }

}
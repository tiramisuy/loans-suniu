package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class AlipayChargeAccount implements Serializable{

	private static final long serialVersionUID = 440600660808000146L;
	
	@JsonProperty("account_id")
    private String accountId;
    @JsonProperty("charge_item")
    private String chargeItem;
    private String area;
    @JsonProperty("charge_unit")
    private String chargeUnit;
    @JsonProperty("charge_account")
    private String chargeAccount;
    @JsonProperty("charge_number")
    private String chargeNumber;
    @JsonProperty("charge_reminder")
    private String chargeReminder;
    @JsonProperty("login_name")
    private String loginName;
    public void setAccountId(String accountId) {
         this.accountId = accountId;
     }
     public String getAccountId() {
         return accountId;
     }

    public void setChargeItem(String chargeItem) {
         this.chargeItem = chargeItem;
     }
     public String getChargeItem() {
         return chargeItem;
     }

    public void setArea(String area) {
         this.area = area;
     }
     public String getArea() {
         return area;
     }

    public void setChargeUnit(String chargeUnit) {
         this.chargeUnit = chargeUnit;
     }
     public String getChargeUnit() {
         return chargeUnit;
     }

    public void setChargeAccount(String chargeAccount) {
         this.chargeAccount = chargeAccount;
     }
     public String getChargeAccount() {
         return chargeAccount;
     }

    public void setChargeNumber(String chargeNumber) {
         this.chargeNumber = chargeNumber;
     }
     public String getChargeNumber() {
         return chargeNumber;
     }

    public void setChargeReminder(String chargeReminder) {
         this.chargeReminder = chargeReminder;
     }
     public String getChargeReminder() {
         return chargeReminder;
     }

    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

}
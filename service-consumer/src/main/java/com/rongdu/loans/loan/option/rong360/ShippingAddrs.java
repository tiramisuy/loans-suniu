package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ShippingAddrs implements Serializable{

	private static final long serialVersionUID = 7563239996411369000L;
	
	@JsonProperty("login_name")
    private String loginName;
    @JsonProperty("addr_id")
    private String addrId;
    private String receiver;
    private String region;
    private String address;
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    @JsonProperty("fixed_phone")
    private String fixedPhone;
    @JsonProperty("email_addr")
    private String emailAddr;
    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

    public void setAddrId(String addrId) {
         this.addrId = addrId;
     }
     public String getAddrId() {
         return addrId;
     }

    public void setReceiver(String receiver) {
         this.receiver = receiver;
     }
     public String getReceiver() {
         return receiver;
     }

    public void setRegion(String region) {
         this.region = region;
     }
     public String getRegion() {
         return region;
     }

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setMobilePhone(String mobilePhone) {
         this.mobilePhone = mobilePhone;
     }
     public String getMobilePhone() {
         return mobilePhone;
     }

    public void setFixedPhone(String fixedPhone) {
         this.fixedPhone = fixedPhone;
     }
     public String getFixedPhone() {
         return fixedPhone;
     }

    public void setEmailAddr(String emailAddr) {
         this.emailAddr = emailAddr;
     }
     public String getEmailAddr() {
         return emailAddr;
     }

}
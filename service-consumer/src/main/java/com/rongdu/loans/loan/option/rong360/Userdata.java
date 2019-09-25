package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Userdata implements Serializable{

	private static final long serialVersionUID = -3053843787711742060L;
	
	@JsonProperty("user_source")
    private String userSource;
    @JsonProperty("id_card")
    private String idCard;
    private String addr;
    @JsonProperty("real_name")
    private String realName;
    private String phone;
    @JsonProperty("phone_remain")
    private String phoneRemain;
    @JsonProperty("reg_time")
    private Date regTime;
    @JsonProperty("update_time")
    private String updateTime;
    private String score;
    @JsonProperty("contact_phone")
    private String contactPhone;
    @JsonProperty("star_level")
    private String starLevel;
    private String authentication;
    @JsonProperty("phone_status")
    private String phoneStatus;
    @JsonProperty("package_name")
    private String packageName;
    public void setUserSource(String userSource) {
         this.userSource = userSource;
     }
     public String getUserSource() {
         return userSource;
     }

    public void setIdCard(String idCard) {
         this.idCard = idCard;
     }
     public String getIdCard() {
         return idCard;
     }

    public void setAddr(String addr) {
         this.addr = addr;
     }
     public String getAddr() {
         return addr;
     }

    public void setRealName(String realName) {
         this.realName = realName;
     }
     public String getRealName() {
         return realName;
     }

    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

    public void setPhoneRemain(String phoneRemain) {
         this.phoneRemain = phoneRemain;
     }
     public String getPhoneRemain() {
         return phoneRemain;
     }

    public void setRegTime(Date regTime) {
         this.regTime = regTime;
     }
     public Date getRegTime() {
         return regTime;
     }

    public void setUpdateTime(String updateTime) {
         this.updateTime = updateTime;
     }
     public String getUpdateTime() {
         return updateTime;
     }

    public void setScore(String score) {
         this.score = score;
     }
     public String getScore() {
         return score;
     }

    public void setContactPhone(String contactPhone) {
         this.contactPhone = contactPhone;
     }
     public String getContactPhone() {
         return contactPhone;
     }

    public void setStarLevel(String starLevel) {
         this.starLevel = starLevel;
     }
     public String getStarLevel() {
         return starLevel;
     }

    public void setAuthentication(String authentication) {
         this.authentication = authentication;
     }
     public String getAuthentication() {
         return authentication;
     }

    public void setPhoneStatus(String phoneStatus) {
         this.phoneStatus = phoneStatus;
     }
     public String getPhoneStatus() {
         return phoneStatus;
     }

    public void setPackageName(String packageName) {
         this.packageName = packageName;
     }
     public String getPackageName() {
         return packageName;
     }

}
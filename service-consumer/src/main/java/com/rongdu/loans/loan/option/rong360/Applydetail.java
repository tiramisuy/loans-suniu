package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Applydetail implements Serializable{

	private static final long serialVersionUID = 2609277616852472942L;
	
	@JsonProperty("asset_auto_type")
    private String assetAutoType;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("work_period")
    private String workPeriod;
    @JsonProperty("user_social_security")
    private String userSocialSecurity;
    @JsonProperty("user_education")
    private String userEducation;
    @JsonProperty("bureau_user_name")
    private String bureauUserName;
    @JsonProperty("is_op_type")
    private String isOpType;
    @JsonProperty("max_monthly_repayment")
    private int maxMonthlyRepayment;
    @JsonProperty("gps_location")
    private String gpsLocation;
    @JsonProperty("user_income_by_card")
    private int userIncomeByCard;
    @JsonProperty("phone_number_house")
    private String phoneNumberHouse;
    private String platform;
    @JsonProperty("device_num")
    private String deviceNum;
    @JsonProperty("ip_address")
    private String ipAddress;
    @JsonProperty("tele_num")
    private String teleNum;
    @JsonProperty("tele_name")
    private String teleName;
    @JsonProperty("phone_brand")
    private String phoneBrand;
    @JsonProperty("device_info")
    private String deviceInfo;
    @JsonProperty("GPS_Address")
    private String gpsAddress;
    public void setAssetAutoType(String assetAutoType) {
         this.assetAutoType = assetAutoType;
     }
     public String getAssetAutoType() {
         return assetAutoType;
     }

    public void setUserId(String userId) {
         this.userId = userId;
     }
     public String getUserId() {
         return userId;
     }

    public void setWorkPeriod(String workPeriod) {
         this.workPeriod = workPeriod;
     }
     public String getWorkPeriod() {
         return workPeriod;
     }

    public void setUserSocialSecurity(String userSocialSecurity) {
         this.userSocialSecurity = userSocialSecurity;
     }
     public String getUserSocialSecurity() {
         return userSocialSecurity;
     }

    public void setUserEducation(String userEducation) {
         this.userEducation = userEducation;
     }
     public String getUserEducation() {
         return userEducation;
     }

    public void setBureauUserName(String bureauUserName) {
         this.bureauUserName = bureauUserName;
     }
     public String getBureauUserName() {
         return bureauUserName;
     }

    public void setIsOpType(String isOpType) {
         this.isOpType = isOpType;
     }
     public String getIsOpType() {
         return isOpType;
     }

    public void setMaxMonthlyRepayment(int maxMonthlyRepayment) {
         this.maxMonthlyRepayment = maxMonthlyRepayment;
     }
     public int getMaxMonthlyRepayment() {
         return maxMonthlyRepayment;
     }

    public void setGpsLocation(String gpsLocation) {
         this.gpsLocation = gpsLocation;
     }
     public String getGpsLocation() {
         return gpsLocation;
     }

    public void setUserIncomeByCard(int userIncomeByCard) {
         this.userIncomeByCard = userIncomeByCard;
     }
     public int getUserIncomeByCard() {
         return userIncomeByCard;
     }

    public void setPhoneNumberHouse(String phoneNumberHouse) {
         this.phoneNumberHouse = phoneNumberHouse;
     }
     public String getPhoneNumberHouse() {
         return phoneNumberHouse;
     }

    public void setPlatform(String platform) {
         this.platform = platform;
     }
     public String getPlatform() {
         return platform;
     }

    public void setDeviceNum(String deviceNum) {
         this.deviceNum = deviceNum;
     }
     public String getDeviceNum() {
         return deviceNum;
     }

    public void setIpAddress(String ipAddress) {
         this.ipAddress = ipAddress;
     }
     public String getIpAddress() {
         return ipAddress;
     }

    public void setTeleNum(String teleNum) {
         this.teleNum = teleNum;
     }
     public String getTeleNum() {
         return teleNum;
     }

    public void setTeleName(String teleName) {
         this.teleName = teleName;
     }
     public String getTeleName() {
         return teleName;
     }

    public void setPhoneBrand(String phoneBrand) {
         this.phoneBrand = phoneBrand;
     }
     public String getPhoneBrand() {
         return phoneBrand;
     }

    public void setDeviceInfo(String deviceInfo) {
         this.deviceInfo = deviceInfo;
     }
     public String getDeviceInfo() {
         return deviceInfo;
     }

    public void setGpsAddress(String gpsAddress) {
         this.gpsAddress = gpsAddress;
     }
     public String getGpsAddress() {
         return gpsAddress;
     }

}
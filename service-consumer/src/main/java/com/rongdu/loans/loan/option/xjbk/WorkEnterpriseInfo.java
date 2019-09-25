package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class WorkEnterpriseInfo implements Serializable{

    private static final long serialVersionUID = 8461559173706705246L;
    @JsonProperty("user_position")
    private String userPosition;
    private String share;
    @JsonProperty("is_market")
    private String isMarket;
    @JsonProperty("true_operation_address")
    private String trueOperationAddress;
    private String industry;
    @JsonProperty("manage_type")
    private String manageType;
    @JsonProperty("is_license")
    private String isLicense;
    @JsonProperty("manage_life_time")
    private String manageLifeTime;
    @JsonProperty("total_revenue")
    private String totalRevenue;
    @JsonProperty("public_revenue")
    private String publicRevenue;
    @JsonProperty("private_revenue")
    private String privateRevenue;
    @JsonProperty("settle_revenue")
    private String settleRevenue;
    @JsonProperty("company_name")
    private String companyName;
    public void setUserPosition(String userPosition) {
         this.userPosition = userPosition;
     }
     public String getUserPosition() {
         return userPosition;
     }

    public void setShare(String share) {
         this.share = share;
     }
     public String getShare() {
         return share;
     }

    public void setIsMarket(String isMarket) {
         this.isMarket = isMarket;
     }
     public String getIsMarket() {
         return isMarket;
     }

    public void setTrueOperationAddress(String trueOperationAddress) {
         this.trueOperationAddress = trueOperationAddress;
     }
     public String getTrueOperationAddress() {
         return trueOperationAddress;
     }

    public void setIndustry(String industry) {
         this.industry = industry;
     }
     public String getIndustry() {
         return industry;
     }

    public void setManageType(String manageType) {
         this.manageType = manageType;
     }
     public String getManageType() {
         return manageType;
     }

    public void setIsLicense(String isLicense) {
         this.isLicense = isLicense;
     }
     public String getIsLicense() {
         return isLicense;
     }

    public void setManageLifeTime(String manageLifeTime) {
         this.manageLifeTime = manageLifeTime;
     }
     public String getManageLifeTime() {
         return manageLifeTime;
     }

    public void setTotalRevenue(String totalRevenue) {
         this.totalRevenue = totalRevenue;
     }
     public String getTotalRevenue() {
         return totalRevenue;
     }

    public void setPublicRevenue(String publicRevenue) {
         this.publicRevenue = publicRevenue;
     }
     public String getPublicRevenue() {
         return publicRevenue;
     }

    public void setPrivateRevenue(String privateRevenue) {
         this.privateRevenue = privateRevenue;
     }
     public String getPrivateRevenue() {
         return privateRevenue;
     }

    public void setSettleRevenue(String settleRevenue) {
         this.settleRevenue = settleRevenue;
     }
     public String getSettleRevenue() {
         return settleRevenue;
     }

    public void setCompanyName(String companyName) {
         this.companyName = companyName;
     }
     public String getCompanyName() {
         return companyName;
     }

}
package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class WorkSoleInfo implements Serializable{

    private static final long serialVersionUID = 9013294539735809925L;
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

}
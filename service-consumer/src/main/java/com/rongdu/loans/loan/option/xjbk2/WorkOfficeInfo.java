package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class WorkOfficeInfo implements Serializable{

    private static final long serialVersionUID = -4315674085171203353L;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("company_address")
    private String companyAddress;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("work_age")
    private String workAge;
    @JsonProperty("pay_type")
    private String payType;
    private String revenue;
    @JsonProperty("company_areas")
    private String companyAreas;
    @JsonProperty("company_tel")
    private String companyTel;
    @JsonProperty("tel_area")
    private String telArea;
    @JsonProperty("tel_no")
    private String telNo;
    public void setCompanyName(String companyName) {
         this.companyName = companyName;
     }
     public String getCompanyName() {
         return companyName;
     }

    public void setCompanyAddress(String companyAddress) {
         this.companyAddress = companyAddress;
     }
     public String getCompanyAddress() {
         return companyAddress;
     }

    public void setCompanyType(String companyType) {
         this.companyType = companyType;
     }
     public String getCompanyType() {
         return companyType;
     }

    public void setWorkAge(String workAge) {
         this.workAge = workAge;
     }
     public String getWorkAge() {
         return workAge;
     }

    public void setPayType(String payType) {
         this.payType = payType;
     }
     public String getPayType() {
         return payType;
     }

    public void setRevenue(String revenue) {
         this.revenue = revenue;
     }
     public String getRevenue() {
         return revenue;
     }

    public void setCompanyAreas(String companyAreas) {
         this.companyAreas = companyAreas;
     }
     public String getCompanyAreas() {
         return companyAreas;
     }

    public void setCompanyTel(String companyTel) {
         this.companyTel = companyTel;
     }
     public String getCompanyTel() {
         return companyTel;
     }

    public void setTelArea(String telArea) {
         this.telArea = telArea;
     }
     public String getTelArea() {
         return telArea;
     }

    public void setTelNo(String telNo) {
         this.telNo = telNo;
     }
     public String getTelNo() {
         return telNo;
     }

}
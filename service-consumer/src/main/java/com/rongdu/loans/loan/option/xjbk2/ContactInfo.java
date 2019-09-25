package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ContactInfo {

    private String name;
    private String mobile;
    private String relation;
    @JsonProperty("name_spare")
    private String nameSpare;
    @JsonProperty("mobile_spare")
    private String mobileSpare;
    @JsonProperty("relation_spare")
    private String relationSpare;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setMobile(String mobile) {
         this.mobile = mobile;
     }
     public String getMobile() {
         return mobile;
     }

    public void setRelation(String relation) {
         this.relation = relation;
     }
     public String getRelation() {
         return relation;
     }

    public void setNameSpare(String nameSpare) {
         this.nameSpare = nameSpare;
     }
     public String getNameSpare() {
         return nameSpare;
     }

    public void setMobileSpare(String mobileSpare) {
         this.mobileSpare = mobileSpare;
     }
     public String getMobileSpare() {
         return mobileSpare;
     }

    public void setRelationSpare(String relationSpare) {
         this.relationSpare = relationSpare;
     }
     public String getRelationSpare() {
         return relationSpare;
     }

}
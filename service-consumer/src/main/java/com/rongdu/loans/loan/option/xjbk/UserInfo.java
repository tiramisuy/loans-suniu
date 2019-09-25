package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class UserInfo implements Serializable{

    private static final long serialVersionUID = -2605417710063507580L;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_phone")
    private String userPhone;
    @JsonProperty("user_idcard")
    private String userIdcard;
    private String degree;
    @JsonProperty("family_info")
    private FamilyInfo familyInfo;
    @JsonProperty("loan_use")
    private String loanUse;
    public void setUserName(String userName) {
         this.userName = userName;
     }
     public String getUserName() {
         return userName;
     }

    public void setUserPhone(String userPhone) {
         this.userPhone = userPhone;
     }
     public String getUserPhone() {
         return userPhone;
     }

    public void setUserIdcard(String userIdcard) {
         this.userIdcard = userIdcard;
     }
     public String getUserIdcard() {
         return userIdcard;
     }

    public void setDegree(String degree) {
         this.degree = degree;
     }
     public String getDegree() {
         return degree;
     }

    public void setFamilyInfo(FamilyInfo familyInfo) {
         this.familyInfo = familyInfo;
     }
     public FamilyInfo getFamilyInfo() {
         return familyInfo;
     }

    public void setLoanUse(String loanUse) {
         this.loanUse = loanUse;
     }
     public String getLoanUse() {
         return loanUse;
     }

}
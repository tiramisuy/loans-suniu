package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class UserInfo implements Serializable{

	private static final long serialVersionUID = -2196502444821851809L;
	
	@JsonProperty("login_name")
    private String loginName;
    private String username;
    @JsonProperty("set_login_name")
    private String setLoginName;
    private String nickname;
    private String sex;
    private String birthday;
    private String hobbies;
    private String email;
    @JsonProperty("real_name")
    private String realName;
    private String marriage;
    private String income;
    @JsonProperty("id_card")
    private String idCard;
    private String education;
    private String industry;
    @JsonProperty("is_qq_bound")
    private String isQqBound;
    @JsonProperty("is_wechat_bound")
    private String isWechatBound;
    @JsonProperty("account_grade")
    private String accountGrade;
    @JsonProperty("account_type")
    private String accountType;
    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

    public void setUsername(String username) {
         this.username = username;
     }
     public String getUsername() {
         return username;
     }

    public void setSetLoginName(String setLoginName) {
         this.setLoginName = setLoginName;
     }
     public String getSetLoginName() {
         return setLoginName;
     }

    public void setNickname(String nickname) {
         this.nickname = nickname;
     }
     public String getNickname() {
         return nickname;
     }

    public void setSex(String sex) {
         this.sex = sex;
     }
     public String getSex() {
         return sex;
     }

    public void setBirthday(String birthday) {
         this.birthday = birthday;
     }
     public String getBirthday() {
         return birthday;
     }

    public void setHobbies(String hobbies) {
         this.hobbies = hobbies;
     }
     public String getHobbies() {
         return hobbies;
     }

    public void setEmail(String email) {
         this.email = email;
     }
     public String getEmail() {
         return email;
     }

    public void setRealName(String realName) {
         this.realName = realName;
     }
     public String getRealName() {
         return realName;
     }

    public void setMarriage(String marriage) {
         this.marriage = marriage;
     }
     public String getMarriage() {
         return marriage;
     }

    public void setIncome(String income) {
         this.income = income;
     }
     public String getIncome() {
         return income;
     }

    public void setIdCard(String idCard) {
         this.idCard = idCard;
     }
     public String getIdCard() {
         return idCard;
     }

    public void setEducation(String education) {
         this.education = education;
     }
     public String getEducation() {
         return education;
     }

    public void setIndustry(String industry) {
         this.industry = industry;
     }
     public String getIndustry() {
         return industry;
     }

    public void setIsQqBound(String isQqBound) {
         this.isQqBound = isQqBound;
     }
     public String getIsQqBound() {
         return isQqBound;
     }

    public void setIsWechatBound(String isWechatBound) {
         this.isWechatBound = isWechatBound;
     }
     public String getIsWechatBound() {
         return isWechatBound;
     }

    public void setAccountGrade(String accountGrade) {
         this.accountGrade = accountGrade;
     }
     public String getAccountGrade() {
         return accountGrade;
     }

    public void setAccountType(String accountType) {
         this.accountType = accountType;
     }
     public String getAccountType() {
         return accountType;
     }

}
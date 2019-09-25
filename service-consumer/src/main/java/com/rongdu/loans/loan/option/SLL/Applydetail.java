package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Applydetail implements Serializable {

    private static final long serialVersionUID = -5470564217952763499L;
    @JsonProperty("application_amount")
    private String applicationAmount;
    @JsonProperty("loan_apply_term")
    private int loanApplyTerm;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("bureau_user_name")
    private String bureauUserName;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_education")
    private String userEducation;
    @JsonProperty("is_op_type")
    private String isOpType;
    @JsonProperty("corporate_flow")
    private String corporateFlow;
    @JsonProperty("operating_year")
    private String operatingYear;
    @JsonProperty("user_social_security")
    private String userSocialSecurity;
    @JsonProperty("work_period")
    private String workPeriod;
    @JsonProperty("user_income_by_card")
    private String userIncomeByCard;
    @JsonProperty("time_enrollment")
    private String timeEnrollment;
    @JsonProperty("school_name")
    private String schoolName;
    @JsonProperty("monthly_average_income")
    private String monthlyAverageIncome;
    @JsonProperty("phone_number_house")
    private String phoneNumberHouse;
    @JsonProperty("ip_address")
    private String ipAddress;

}
package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-03 10:38:10
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Applydetail implements Serializable{

	private static final long serialVersionUID = 1360615415942868960L;
	
	@JsonProperty("bureau_user_name")
    private String bureauUserName;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("application_amount")
    private String applicationAmount;
    @JsonProperty("loan_apply_term")
    private int loanApplyTerm;
    @JsonProperty("max_monthly_repayment")
    private String maxMonthlyRepayment;
    @JsonProperty("user_education")
    private String userEducation;
    @JsonProperty("user_income_by_card")
    private String userIncomeByCard;
    @JsonProperty("work_period")
    private String workPeriod;
    @JsonProperty("monthly_average_income")
    private String monthlyAverageIncome;
    @JsonProperty("phone_number_house")
    private String phoneNumberHouse;
    @JsonProperty("user_social_security")
    private String userSocialSecurity;
    @JsonProperty("corporate_flow")
    private String corporateFlow;
    @JsonProperty("operating_year")
    private String operatingYear;
    @JsonProperty("is_op_type")
    private String isOpType;
    @JsonProperty("asset_auto_type")
    private String assetAutoType;
    @JsonProperty("credit_status")
    private String creditStatus;
    @JsonProperty("asset_house_type")
    private String assetHouseType;
    @JsonProperty("if_social_security")
    private String ifSocialSecurity;
    @JsonProperty("if_provident_fund")
    private String ifProvidentFund;
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
    @JsonProperty("gps_location")
    private String gpsLocation;
    @JsonProperty("GPS_Address")
    private String gpsAddress;

}
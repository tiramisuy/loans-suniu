package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.loans.loan.option.xjbk2.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class UserAdditional implements Serializable{

    private static final long serialVersionUID = -3727364620586179029L;
    @JsonProperty("device_info")
    private DeviceInfo deviceInfo;
    @JsonProperty("address_book")
    private AddressBook addressBook;
    @JsonProperty("contact_info")
    private ContactInfo contactInfo;
    @JsonProperty("work_office_info")
    private WorkOfficeInfo workOfficeInfo;
    @JsonProperty("work_enterprise_info")
    private WorkEnterpriseInfo workEnterpriseInfo;
    @JsonProperty("work_student_info")
    private WorkStudentInfo workStudentInfo;
    @JsonProperty("work_sole_info")
    private WorkSoleInfo workSoleInfo;
    @JsonProperty("work_free_info")
    private WorkFreeInfo workFreeInfo;
    @JsonProperty("car_info")
    private CarInfo carInfo;
    @JsonProperty("house_info")
    private HouseInfo houseInfo;
    @JsonProperty("profession_type")
    private ProfessionType professionType;
    @JsonProperty("business_type")
    private BusinessType businessType;
    @JsonProperty("credit_info")
    private CreditInfo creditInfo;
    @JsonProperty("social_security")
    private SocialSecurity socialSecurity;
    @JsonProperty("housing_security")
    private HousingSecurity housingSecurity;
    private Marriage marriage;
    @JsonProperty("revenue_source")
    private RevenueSource revenueSource;
    @JsonProperty("is_loan")
    private IsLoan isLoan;
    private Wechat wechat;
    private Email email;
    private Qq qq;



    @JsonProperty("loan_use")
    private LoanUse loanUse;
    @JsonProperty("education_info")
    private EducationInfo educationInfo;
    @JsonProperty("family_info")
    private com.rongdu.loans.loan.option.xjbk2.FamilyInfo familyInfo;

    @JsonProperty("work_info")
    private WorkInfo workInfo;




}
package com.rongdu.loans.loan.option.dwd;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2018-10-30 16:11:56
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class DWDAdditionInfo implements Serializable {

    private static final long serialVersionUID = 1828660817807232014L;
    @JsonProperty("addr_detail")
    private String addrDetail;
    @JsonProperty("amount_of_staff")
    private String amountOfStaff;
    @JsonProperty("asset_auto_type")
    private String assetAutoType;
    @JsonProperty("company_addr_detail")
    private String companyAddrDetail;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("contact1A_name")
    private String contact1aName;
    @JsonProperty("contact1A_number")
    private String contact1aNumber;
    @JsonProperty("contact1A_relationship")
    private String contact1aRelationship;
    private Contacts contacts;
    @JsonProperty("credite_status")
    private String crediteStatus;
    @JsonProperty("device_info_all")
    private DeviceInfoAll deviceInfoAll;
    @JsonProperty("emergency_contact_personA_name")
    private String emergencyContactPersonaName;
    @JsonProperty("emergency_contact_personA_phone")
    private String emergencyContactPersonaPhone;
    @JsonProperty("emergency_contact_personA_relationship")
    private String emergencyContactPersonaRelationship;
    @JsonProperty("family_live_type")
    private String familyLiveType;
    private Date hiredate;
    @JsonProperty("household_type")
    private String householdType;
    @JsonProperty("iD_Negative")
    private List<String> idNegative;
    @JsonProperty("iD_Positive")
    private List<String> idPositive;
    @JsonProperty("income_type")
    private String incomeType;
    @JsonProperty("industry_type")
    private String industryType;
    @JsonProperty("is_simulator")
    private String isSimulator;
    @JsonProperty("loan_use")
    private String loanUse;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("photo_assay")
    private List<String> photoAssay;
    @JsonProperty("photo_hand_ID")
    private List<String> photoHandId;
    private String position;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("user_marriage")
    private String userMarriage;
    @JsonProperty("ext")
    private String ext;

    private String channelCode;
}
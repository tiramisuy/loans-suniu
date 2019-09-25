package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * Auto-generated: 2018-12-10 13:56:59
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class AddData implements Serializable {

    private static final long serialVersionUID = -5430512092060698170L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("familiy_live_type")
    private String familiyLiveType;
    @JsonProperty("family_live_type")
    private String familyLiveType;
    @JsonProperty("addr_detail")
    private String addrDetail;
    @JsonProperty("district_code")
    private String districtCode;
    @JsonProperty("user_marriage")
    private String userMarriage;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("ip_address")
    private String ipAddress;
    @JsonProperty("asset_auto_type")
    private String assetAutoType;
    @JsonProperty("credit_status")
    private String creditStatus;
    @JsonProperty("liability_status")
    private String liabilityStatus;
    @JsonProperty("user_qq")
    private String userQq;
    @JsonProperty("loan_use")
    private String loanUse;
    @JsonProperty("household_type")
    private String householdType;
    @JsonProperty("credit_card_no")
    private String creditCardNo;
    @JsonProperty("credit_card_mobile")
    private String creditCardMobile;
    @JsonProperty("user_wechat")
    private String userWechat;
    @JsonProperty("emergency_contact_personA_relationship")
    private String emergencyContactPersonaRelationship;
    @JsonProperty("emergency_contact_personA_name")
    private String emergencyContactPersonaName;
    @JsonProperty("emergency_contact_personA_phone")
    private String emergencyContactPersonaPhone;
    @JsonProperty("emergency_contact_personB_relationship")
    private String emergencyContactPersonbRelationship;
    @JsonProperty("emergency_contact_personB_name")
    private String emergencyContactPersonbName;
    @JsonProperty("emergency_contact_personB_phone")
    private String emergencyContactPersonbPhone;
    @JsonProperty("contract1A_number")
    private String contract1aNumber;
    @JsonProperty("contract1A_name")
    private String contract1aName;
    @JsonProperty("contract1A_relationship")
    private String contract1aRelationship;
    @JsonProperty("contact1A_number")
    private String contact1aNumber;
    @JsonProperty("contact1A_name")
    private String contact1aName;
    @JsonProperty("contact1A_relationship")
    private String contact1aRelationship;
    @JsonProperty("ID_Positive")
    private List<String> idPositive;
    @JsonProperty("ID_Negative")
    private List<String> idNegative;
    @JsonProperty("Name_OCR")
    private String nameOcr;
    @JsonProperty("ID_Ethnic_OCR")
    private String idEthnicOcr;
    @JsonProperty("ID_Address_OCR")
    private String idAddressOcr;
    @JsonProperty("ID_Number_OCR")
    private String idNumberOcr;
    @JsonProperty("ID_Sex_OCR")
    private String idSexOcr;
    @JsonProperty("ID_Issue_Org_OCR")
    private String idIssueOrgOcr;
    @JsonProperty("ID_Due_time_OCR")
    private String idDueTimeOcr;
    @JsonProperty("ID_Effect_time_OCR")
    private String idEffectTimeOcr;
    @JsonProperty("photo_score")
    private PhotoScore photoScore;
    @JsonProperty("photo_assay")
    private List<String> photoAssay;
    private Contacts contacts;
    @JsonProperty("device_info_all")
    private String deviceInfoAll;
    @JsonProperty("is_simulator")
    private int isSimulator;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("company_addr_detail")
    private String companyAddrDetail;
    @JsonProperty("company_number")
    private String companyNumber;


}
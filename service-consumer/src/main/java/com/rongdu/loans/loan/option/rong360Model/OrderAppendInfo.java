package com.rongdu.loans.loan.option.rong360Model;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-03 10:40:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OrderAppendInfo implements Serializable{

	private static final long serialVersionUID = -6268150022564621044L;
	
	@JsonProperty("ID_Positive")
    private List<String> idPositive;
    @JsonProperty("Name_OCR")
    private String nameOcr;
    @JsonProperty("ID_Sex_OCR")
    private String idSexOcr;
    @JsonProperty("ID_Ethnic_OCR")
    private String idEthnicOcr;
    @JsonProperty("ID_Address_OCR")
    private String idAddressOcr;
    @JsonProperty("ID_Number_OCR")
    private String idNumberOcr;
    @JsonProperty("ID_Negative")
    private List<String> idNegative;
    @JsonProperty("ID_Issue_Org_OCR")
    private String idIssueOrgOcr;
    @JsonProperty("ID_Due_time_OCR")
    private String idDueTimeOcr;
    @JsonProperty("photo_hand_ID")
    private List<String> photoHandId;
    @JsonProperty("emergency_contact_personB_phone")
    private String emergencyContactPersonbPhone;
    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("emergency_contact_personA_relationship")
    private String emergencyContactPersonaRelationship;
    @JsonProperty("emergency_contact_personB_relationship")
    private String emergencyContactPersonbRelationship;
    @JsonProperty("addr_detail")
    private String addrDetail;
    @JsonProperty("emergency_contact_personA_phone")
    private String emergencyContactPersonaPhone;
    @JsonProperty("emergency_contact_personB_name")
    private String emergencyContactPersonbName;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("company_addr_detail")
    private String companyAddrDetail;
    @JsonProperty("emergency_contact_personA_name")
    private String emergencyContactPersonaName;
    private Contacts contacts;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("sns_qq")
    private String snsQq;
    @JsonProperty("loan_use_type")
    private String loanUseType;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("scores_assay_face")
    private String scoresAssayFace;
    @JsonProperty("scores_range_assay_face")
    private String scoresRangeAssayFace;
    @JsonProperty("genuineness_face")
    private String genuinenessFace;
    @JsonProperty("photo_assay")
    private List<String> photoAssay;
    @JsonProperty("photo_assay_time")
    private String photoAssayTime;
    @JsonProperty("delta_face")
    private String deltaFace;
    @JsonProperty("assay_type")
    private String assayType;
    @JsonProperty("is_simulator")
    private int isSimulator;
    private int id;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("application_amount")
    private String applicationAmount;
    @JsonProperty("application_term")
    private int applicationTerm;
    @JsonProperty("term_unit")
    private int termUnit;
    @JsonProperty("application_term_new")
    private int applicationTermNew;

}
package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 11:7:0
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class OrderAppendInfo implements Serializable{

	private static final long serialVersionUID = 4440098523237236668L;
	
	@JsonProperty("ID_Issue_Org_OCR")
    private String idIssueOrgOcr;
    @JsonProperty("ID_Due_time_OCR")
    private String idDueTimeOcr;
    @JsonProperty("ID_Negative")
    private List<String> idNegative;
    @JsonProperty("emergency_contact_personA_relationship")
    private String emergencyContactPersonaRelationship;
    @JsonProperty("user_marriage")
    private String userMarriage;
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
    @JsonProperty("ID_Positive")
    private List<String> idPositive;
    @JsonProperty("major_groups")
    private String majorGroups;
    @JsonProperty("device_info_all")
    private String deviceInfoAll;
    @JsonProperty("photo_hand_ID")
    private List<String> photoHandId;
    @JsonProperty("loan_use_type")
    private String loanUseType;
    @JsonProperty("scores_assay_face")
    private String scoresAssayFace;
    @JsonProperty("scores_range_assay_face")
    private String scoresRangeAssayFace;
    @JsonProperty("photo_assay")
    private List<String> photoAssay;
    @JsonProperty("photo_assay_time")
    private String photoAssayTime;
    @JsonProperty("delta_face")
    private String deltaFace;
    @JsonProperty("scores_assay")
    private String scoresAssay;
    @JsonProperty("assay_type")
    private String assayType;
    @JsonProperty("is_simulator")
    private int isSimulator;
    private Contacts contacts;
    @JsonProperty("order_no")
    private String orderNo;
    private int id;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("application_amount")
    private String applicationAmount;
    @JsonProperty("application_term")
    private int applicationTerm;
    public void setIdIssueOrgOcr(String idIssueOrgOcr) {
         this.idIssueOrgOcr = idIssueOrgOcr;
     }
     public String getIdIssueOrgOcr() {
         return idIssueOrgOcr;
     }

    public void setIdDueTimeOcr(String idDueTimeOcr) {
         this.idDueTimeOcr = idDueTimeOcr;
     }
     public String getIdDueTimeOcr() {
         return idDueTimeOcr;
     }

    public void setIdNegative(List<String> idNegative) {
         this.idNegative = idNegative;
     }
     public List<String> getIdNegative() {
         return idNegative;
     }

    public void setEmergencyContactPersonaRelationship(String emergencyContactPersonaRelationship) {
         this.emergencyContactPersonaRelationship = emergencyContactPersonaRelationship;
     }
     public String getEmergencyContactPersonaRelationship() {
         return emergencyContactPersonaRelationship;
     }

    public void setUserMarriage(String userMarriage) {
         this.userMarriage = userMarriage;
     }
     public String getUserMarriage() {
         return userMarriage;
     }

    public void setNameOcr(String nameOcr) {
         this.nameOcr = nameOcr;
     }
     public String getNameOcr() {
         return nameOcr;
     }

    public void setIdSexOcr(String idSexOcr) {
         this.idSexOcr = idSexOcr;
     }
     public String getIdSexOcr() {
         return idSexOcr;
     }

    public void setIdEthnicOcr(String idEthnicOcr) {
         this.idEthnicOcr = idEthnicOcr;
     }
     public String getIdEthnicOcr() {
         return idEthnicOcr;
     }

    public void setIdAddressOcr(String idAddressOcr) {
         this.idAddressOcr = idAddressOcr;
     }
     public String getIdAddressOcr() {
         return idAddressOcr;
     }

    public void setIdNumberOcr(String idNumberOcr) {
         this.idNumberOcr = idNumberOcr;
     }
     public String getIdNumberOcr() {
         return idNumberOcr;
     }

    public void setIdPositive(List<String> idPositive) {
         this.idPositive = idPositive;
     }
     public List<String> getIdPositive() {
         return idPositive;
     }

    public void setMajorGroups(String majorGroups) {
         this.majorGroups = majorGroups;
     }
     public String getMajorGroups() {
         return majorGroups;
     }

    public void setDeviceInfoAll(String deviceInfoAll) {
         this.deviceInfoAll = deviceInfoAll;
     }
     public String getDeviceInfoAll() {
         return deviceInfoAll;
     }

    public void setPhotoHandId(List<String> photoHandId) {
         this.photoHandId = photoHandId;
     }
     public List<String> getPhotoHandId() {
         return photoHandId;
     }

    public void setLoanUseType(String loanUseType) {
         this.loanUseType = loanUseType;
     }
     public String getLoanUseType() {
         return loanUseType;
     }

    public void setScoresAssayFace(String scoresAssayFace) {
         this.scoresAssayFace = scoresAssayFace;
     }
     public String getScoresAssayFace() {
         return scoresAssayFace;
     }

    public void setScoresRangeAssayFace(String scoresRangeAssayFace) {
         this.scoresRangeAssayFace = scoresRangeAssayFace;
     }
     public String getScoresRangeAssayFace() {
         return scoresRangeAssayFace;
     }

    public void setPhotoAssay(List<String> photoAssay) {
         this.photoAssay = photoAssay;
     }
     public List<String> getPhotoAssay() {
         return photoAssay;
     }

    public void setPhotoAssayTime(String photoAssayTime) {
         this.photoAssayTime = photoAssayTime;
     }
     public String getPhotoAssayTime() {
         return photoAssayTime;
     }

    public void setDeltaFace(String deltaFace) {
         this.deltaFace = deltaFace;
     }
     public String getDeltaFace() {
         return deltaFace;
     }

    public void setScoresAssay(String scoresAssay) {
         this.scoresAssay = scoresAssay;
     }
     public String getScoresAssay() {
         return scoresAssay;
     }

    public void setAssayType(String assayType) {
         this.assayType = assayType;
     }
     public String getAssayType() {
         return assayType;
     }

    public void setIsSimulator(int isSimulator) {
         this.isSimulator = isSimulator;
     }
     public int getIsSimulator() {
         return isSimulator;
     }

    public void setContacts(Contacts contacts) {
         this.contacts = contacts;
     }
     public Contacts getContacts() {
         return contacts;
     }

    public void setOrderNo(String orderNo) {
         this.orderNo = orderNo;
     }
     public String getOrderNo() {
         return orderNo;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setUserId(int userId) {
         this.userId = userId;
     }
     public int getUserId() {
         return userId;
     }

    public void setProductId(int productId) {
         this.productId = productId;
     }
     public int getProductId() {
         return productId;
     }

    public void setApplicationAmount(String applicationAmount) {
         this.applicationAmount = applicationAmount;
     }
     public String getApplicationAmount() {
         return applicationAmount;
     }

    public void setApplicationTerm(int applicationTerm) {
         this.applicationTerm = applicationTerm;
     }
     public int getApplicationTerm() {
         return applicationTerm;
     }

}
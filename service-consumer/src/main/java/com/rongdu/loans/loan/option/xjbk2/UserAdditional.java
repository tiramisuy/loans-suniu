package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class UserAdditional {

    private Qq qq;
    @JsonProperty("loan_use")
    private LoanUse loanUse;
    @JsonProperty("education_info")
    private EducationInfo educationInfo;
    @JsonProperty("family_info")
    private FamilyInfo familyInfo;
    private Marriage marriage;
    @JsonProperty("work_info")
    private WorkInfo workInfo;
    @JsonProperty("device_info")
    private DeviceInfo deviceInfo;
    @JsonProperty("address_book")
    private AddressBook addressBook;
    @JsonProperty("contact_info")
    private ContactInfo contactInfo;
    public void setQq(Qq qq) {
         this.qq = qq;
     }
     public Qq getQq() {
         return qq;
     }

    public void setLoanUse(LoanUse loanUse) {
         this.loanUse = loanUse;
     }
     public LoanUse getLoanUse() {
         return loanUse;
     }

    public void setEducationInfo(EducationInfo educationInfo) {
         this.educationInfo = educationInfo;
     }
     public EducationInfo getEducationInfo() {
         return educationInfo;
     }

    public void setFamilyInfo(FamilyInfo familyInfo) {
         this.familyInfo = familyInfo;
     }
     public FamilyInfo getFamilyInfo() {
         return familyInfo;
     }

    public void setMarriage(Marriage marriage) {
         this.marriage = marriage;
     }
     public Marriage getMarriage() {
         return marriage;
     }

    public void setWorkInfo(WorkInfo workInfo) {
         this.workInfo = workInfo;
     }
     public WorkInfo getWorkInfo() {
         return workInfo;
     }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
         this.deviceInfo = deviceInfo;
     }
     public DeviceInfo getDeviceInfo() {
         return deviceInfo;
     }

    public void setAddressBook(AddressBook addressBook) {
         this.addressBook = addressBook;
     }
     public AddressBook getAddressBook() {
         return addressBook;
     }

    public void setContactInfo(ContactInfo contactInfo) {
         this.contactInfo = contactInfo;
     }
     public ContactInfo getContactInfo() {
         return contactInfo;
     }

}
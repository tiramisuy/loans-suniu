package com.rongdu.loans.loan.option.dwd.report;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class CheckSearchInfo implements Serializable {

    private static final long serialVersionUID = -1229248056180578899L;
    @JsonProperty("phone_with_other_idcards")
    private List<String> phoneWithOtherIdcards;
    @JsonProperty("phone_with_other_names")
    private List<String> phoneWithOtherNames;
    @JsonProperty("arised_open_web")
    private List<String> arisedOpenWeb;
    @JsonProperty("register_org_cnt")
    private int registerOrgCnt;
    @JsonProperty("idcard_with_other_phones")
    private List<String> idcardWithOtherPhones;
    @JsonProperty("searched_org_cnt")
    private int searchedOrgCnt;
    @JsonProperty("searched_org_type")
    private List<String> searchedOrgType;
    @JsonProperty("register_org_type")
    private List<String> registerOrgType;
    @JsonProperty("idcard_with_other_names")
    private List<String> idcardWithOtherNames;
    public void setPhoneWithOtherIdcards(List<String> phoneWithOtherIdcards) {
         this.phoneWithOtherIdcards = phoneWithOtherIdcards;
     }
     public List<String> getPhoneWithOtherIdcards() {
         return phoneWithOtherIdcards;
     }

    public void setPhoneWithOtherNames(List<String> phoneWithOtherNames) {
         this.phoneWithOtherNames = phoneWithOtherNames;
     }
     public List<String> getPhoneWithOtherNames() {
         return phoneWithOtherNames;
     }

    public void setArisedOpenWeb(List<String> arisedOpenWeb) {
         this.arisedOpenWeb = arisedOpenWeb;
     }
     public List<String> getArisedOpenWeb() {
         return arisedOpenWeb;
     }

    public void setRegisterOrgCnt(int registerOrgCnt) {
         this.registerOrgCnt = registerOrgCnt;
     }
     public int getRegisterOrgCnt() {
         return registerOrgCnt;
     }

    public void setIdcardWithOtherPhones(List<String> idcardWithOtherPhones) {
         this.idcardWithOtherPhones = idcardWithOtherPhones;
     }
     public List<String> getIdcardWithOtherPhones() {
         return idcardWithOtherPhones;
     }

    public void setSearchedOrgCnt(int searchedOrgCnt) {
         this.searchedOrgCnt = searchedOrgCnt;
     }
     public int getSearchedOrgCnt() {
         return searchedOrgCnt;
     }

    public void setSearchedOrgType(List<String> searchedOrgType) {
         this.searchedOrgType = searchedOrgType;
     }
     public List<String> getSearchedOrgType() {
         return searchedOrgType;
     }

    public void setRegisterOrgType(List<String> registerOrgType) {
         this.registerOrgType = registerOrgType;
     }
     public List<String> getRegisterOrgType() {
         return registerOrgType;
     }

    public void setIdcardWithOtherNames(List<String> idcardWithOtherNames) {
         this.idcardWithOtherNames = idcardWithOtherNames;
     }
     public List<String> getIdcardWithOtherNames() {
         return idcardWithOtherNames;
     }

}
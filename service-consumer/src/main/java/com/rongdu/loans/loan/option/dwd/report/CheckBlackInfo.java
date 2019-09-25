package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class CheckBlackInfo implements Serializable {

    private static final long serialVersionUID = -5077879429980684950L;
    @JsonProperty("contacts_class1_cnt")
    private int contactsClass1Cnt;
    @JsonProperty("contacts_class1_blacklist_cnt")
    private int contactsClass1BlacklistCnt;
    @JsonProperty("contacts_class2_blacklist_cnt")
    private int contactsClass2BlacklistCnt;
    @JsonProperty("contacts_router_cnt")
    private int contactsRouterCnt;
    @JsonProperty("contacts_router_ratio")
    private double contactsRouterRatio;
    @JsonProperty("phone_gray_score")
    private int phoneGrayScore;
    public void setContactsClass1Cnt(int contactsClass1Cnt) {
         this.contactsClass1Cnt = contactsClass1Cnt;
     }
     public int getContactsClass1Cnt() {
         return contactsClass1Cnt;
     }

    public void setContactsClass1BlacklistCnt(int contactsClass1BlacklistCnt) {
         this.contactsClass1BlacklistCnt = contactsClass1BlacklistCnt;
     }
     public int getContactsClass1BlacklistCnt() {
         return contactsClass1BlacklistCnt;
     }

    public void setContactsClass2BlacklistCnt(int contactsClass2BlacklistCnt) {
         this.contactsClass2BlacklistCnt = contactsClass2BlacklistCnt;
     }
     public int getContactsClass2BlacklistCnt() {
         return contactsClass2BlacklistCnt;
     }

    public void setContactsRouterCnt(int contactsRouterCnt) {
         this.contactsRouterCnt = contactsRouterCnt;
     }
     public int getContactsRouterCnt() {
         return contactsRouterCnt;
     }

    public void setContactsRouterRatio(double contactsRouterRatio) {
         this.contactsRouterRatio = contactsRouterRatio;
     }
     public double getContactsRouterRatio() {
         return contactsRouterRatio;
     }

    public void setPhoneGrayScore(int phoneGrayScore) {
         this.phoneGrayScore = phoneGrayScore;
     }
     public int getPhoneGrayScore() {
         return phoneGrayScore;
     }

}
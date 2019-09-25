package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = 2070629405001422523L;
    @JsonProperty("phone_num_loc")
    private String phoneNumLoc;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("trans_start")
    private String transStart;
    @JsonProperty("call_out_cnt")
    private int callOutCnt;
    @JsonProperty("phone_num")
    private String phoneNum;
    @JsonProperty("call_in_cnt")
    private int callInCnt;
    @JsonProperty("sms_cnt")
    private int smsCnt;
    @JsonProperty("call_len")
    private int callLen;
    @JsonProperty("trans_end")
    private String transEnd;
    public void setPhoneNumLoc(String phoneNumLoc) {
         this.phoneNumLoc = phoneNumLoc;
     }
     public String getPhoneNumLoc() {
         return phoneNumLoc;
     }

    public void setCallCnt(int callCnt) {
         this.callCnt = callCnt;
     }
     public int getCallCnt() {
         return callCnt;
     }

    public void setTransStart(String transStart) {
         this.transStart = transStart;
     }
     public String getTransStart() {
         return transStart;
     }

    public void setCallOutCnt(int callOutCnt) {
         this.callOutCnt = callOutCnt;
     }
     public int getCallOutCnt() {
         return callOutCnt;
     }

    public void setPhoneNum(String phoneNum) {
         this.phoneNum = phoneNum;
     }
     public String getPhoneNum() {
         return phoneNum;
     }

    public void setCallInCnt(int callInCnt) {
         this.callInCnt = callInCnt;
     }
     public int getCallInCnt() {
         return callInCnt;
     }

    public void setSmsCnt(int smsCnt) {
         this.smsCnt = smsCnt;
     }
     public int getSmsCnt() {
         return smsCnt;
     }

    public void setCallLen(int callLen) {
         this.callLen = callLen;
     }
     public int getCallLen() {
         return callLen;
     }

    public void setTransEnd(String transEnd) {
         this.transEnd = transEnd;
     }
     public String getTransEnd() {
         return transEnd;
     }

}
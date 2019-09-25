package com.rongdu.loans.loan.option.jdq.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-24 9:35:34
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Calincntlistv implements Serializable{

    private static final long serialVersionUID = -7587808771849100628L;
    @JsonProperty("terminatingCallCount")
    private int terminatingcallcount;
    @JsonProperty("contactName")
    private String contactname;
    private String mobile;
    @JsonProperty("firstCall")
    private String firstcall;
    @JsonProperty("contact_1m")
    private int contact1m;
    @JsonProperty("call_len")
    private int callLen;
    @JsonProperty("contact_3m")
    private int contact3m;
    @JsonProperty("lastCall")
    private String lastcall;
    /**
     * 呼出时长:秒
     */
    @JsonProperty("originatingTime")
    private int originatingtime;
    /**
     * 呼出时长:时分秒
     */
    @JsonProperty("originatingTimeStr")
    private String originatingTimeStr;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("contact_1w")
    private int contact1w;
    @JsonProperty("belongTo")
    private String belongto;
    /**
     * 呼入时长:秒
     */
    @JsonProperty("terminatingTime")
    private int terminatingtime;
    /**
     * 呼入时长:时分秒
     */
    @JsonProperty("terminatingTimeStr")
    private String terminatingtimeStr;
    @JsonProperty("contact_3m_plus")
    private int contact3mPlus;
    @JsonProperty("originatingCallCount")
    private int originatingcallcount;
}
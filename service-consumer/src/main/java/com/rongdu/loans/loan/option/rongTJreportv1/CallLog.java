package com.rongdu.loans.loan.option.rongTJreportv1;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-19 11:4:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class CallLog implements Serializable{

	private static final long serialVersionUID = -6927419314962426192L;
	
	@JsonProperty("contact_noon")
    private int contactNoon;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    @JsonProperty("contact_3m")
    private int contact3m;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("contact_1m")
    private int contact1m;
    @JsonProperty("unknown_cnt")
    private int unknownCnt;
    @JsonProperty("contact_eveing")
    private int contactEveing;
    @JsonProperty("contact_1w")
    private int contact1w;
    @JsonProperty("phone_info")
    private String phoneInfo;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    private List<Detail> detail;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("contact_weekday")
    private int contactWeekday;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    private String phone;
    @JsonProperty("call_seconds")
    private int callSeconds;
    @JsonProperty("first_contact_date")
    private String firstContactDate;
    @JsonProperty("contact_afternoon")
    private int contactAfternoon;
    @JsonProperty("contact_early_morning")
    private int contactEarlyMorning;
    @JsonProperty("last_contact_date")
    private String lastContactDate;
    @JsonProperty("contact_night")
    private int contactNight;
    @JsonProperty("phone_label")
    private String phoneLabel;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("phone_location")
    private String phoneLocation;
    @JsonProperty("contact_morning")
    private int contactMorning;
    @JsonProperty("contact_weekend")
    private int contactWeekend;
    
    @JsonProperty("contact_name")
    private String contactName;

}
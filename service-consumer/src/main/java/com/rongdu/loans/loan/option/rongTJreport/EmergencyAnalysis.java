package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class EmergencyAnalysis implements Serializable{

	private static final long serialVersionUID = 3122881560534006406L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    private String name;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    @JsonProperty("phone_info")
    private String phoneInfo;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("phone_label")
    private String phoneLabel;
    private String phone;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("last_contact_date")
    private String lastContactDate;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;
    @JsonProperty("first_contact_date")
    private String firstContactDate;

}
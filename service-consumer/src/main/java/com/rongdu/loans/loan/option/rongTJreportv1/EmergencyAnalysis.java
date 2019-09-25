package com.rongdu.loans.loan.option.rongTJreportv1;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-19 11:4:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class EmergencyAnalysis implements Serializable{

	private static final long serialVersionUID = -6379874237312402607L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    private String name;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    private String phone;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("unknown_cnt")
    private int unknownCnt;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("last_contact_date")
    private String lastContactDate;
    @JsonProperty("call_seconds")
    private int callSeconds;
    @JsonProperty("first_contact_date")
    private String firstContactDate;

}
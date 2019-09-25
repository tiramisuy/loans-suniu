package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ContactAreaAnalysis implements Serializable{

	private static final long serialVersionUID = -7137274228419043227L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    private String area;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    @JsonProperty("month_detail")
    private List<MonthDetail> monthDetail;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("contact_phone_cnt")
    private int contactPhoneCnt;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
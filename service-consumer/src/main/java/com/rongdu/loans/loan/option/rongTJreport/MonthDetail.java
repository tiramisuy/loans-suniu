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
public class MonthDetail implements Serializable{

	private static final long serialVersionUID = -5170696312552659879L;
	
	private String month;
	@JsonProperty("talk_cnt")
	private int talkCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("receive_cnt")
    private int receiveCnt;

}
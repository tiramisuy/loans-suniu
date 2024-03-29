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
public class MonthlyConsumption implements Serializable{

	private static final long serialVersionUID = 3694838513497894079L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    private String month;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("call_consumption")
    private double callConsumption;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
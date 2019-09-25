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
public class AreaAnalysis implements Serializable{

	private static final long serialVersionUID = 3183410799970558912L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    private String area;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    private List<Detail> detail;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("unknown_cnt")
    private int unknownCnt;
    @JsonProperty("contact_phone_cnt")
    private int contactPhoneCnt;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
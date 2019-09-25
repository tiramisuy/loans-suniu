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
public class TripAnalysis implements Serializable{

	private static final long serialVersionUID = 1872643382729939645L;
	
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
    @JsonProperty("date_distribution")
    private List<String> dateDistribution;
    private List<Detail> detail;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("unknown_cnt")
    private int unknownCnt;
    private String location;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
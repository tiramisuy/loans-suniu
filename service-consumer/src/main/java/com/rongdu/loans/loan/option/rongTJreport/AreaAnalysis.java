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
public class AreaAnalysis implements Serializable{

	private static final long serialVersionUID = -7348635343820671483L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    private String area;
    @JsonProperty("month_detail")
    private List<MonthDetail> monthDetail;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
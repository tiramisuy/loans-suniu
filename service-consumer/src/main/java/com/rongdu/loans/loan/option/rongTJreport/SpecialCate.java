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
public class SpecialCate implements Serializable{

	private static final long serialVersionUID = 3651644249436532885L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("phone_detail")
    private List<PhoneDetail> phoneDetail;
    private String cate;
    @JsonProperty("talk_cnt")
    private int talkCnt;
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
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
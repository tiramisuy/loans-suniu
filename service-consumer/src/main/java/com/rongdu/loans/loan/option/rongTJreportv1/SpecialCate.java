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
public class SpecialCate implements Serializable{

	private static final long serialVersionUID = 2430770302945103953L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
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
    @JsonProperty("unknown_cnt")
    private int unknownCnt;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("phone_detail")
    private List<PhoneDetail> phoneDetail;
    @JsonProperty("call_seconds")
    private int callSeconds;

}
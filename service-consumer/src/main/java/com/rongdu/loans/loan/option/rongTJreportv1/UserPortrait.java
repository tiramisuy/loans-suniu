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
public class UserPortrait implements Serializable{

	private static final long serialVersionUID = -2197358586250456668L;
	
	@JsonProperty("night_activity_ratio")
    private double nightActivityRatio;
    @JsonProperty("both_call_cnt")
    private int bothCallCnt;
    @JsonProperty("special_call_info")
    private List<SpecialCallInfo> specialCallInfo;
    @JsonProperty("night_msg_ratio")
    private double nightMsgRatio;
    @JsonProperty("contact_distribution")
    private ContactDistribution contactDistribution;
    @JsonProperty("active_days")
    private ActiveDays activeDays;

}
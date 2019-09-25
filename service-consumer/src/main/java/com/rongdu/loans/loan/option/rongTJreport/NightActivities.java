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
public class NightActivities implements Serializable{

	private static final long serialVersionUID = -5770311669124262256L;
	
	@JsonProperty("monthly_avg_seconds_ratio")
    private double monthlyAvgSecondsRatio;
    @JsonProperty("monthly_avg_seconds")
    private double monthlyAvgSeconds;
    @JsonProperty("monthly_avg_msg_ratio")
    private int monthlyAvgMsgRatio;
    @JsonProperty("monthly_avg_msg")
    private int monthlyAvgMsg;

}
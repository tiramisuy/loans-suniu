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
public class SilentDays implements Serializable{

	private static final long serialVersionUID = 6680175295587431392L;
	
	@JsonProperty("start_day")
    private String startDay;
    @JsonProperty("monthly_avg_days")
    private double monthlyAvgDays;
    @JsonProperty("max_detail")
    private List<String> maxDetail;
    @JsonProperty("silent_3_detail")
    private List<String> silent3Detail;
    @JsonProperty("max_interval")
    private int maxInterval;
    @JsonProperty("end_day")
    private String endDay;
    @JsonProperty("silent_detail")
    private List<String> silentDetail;

}
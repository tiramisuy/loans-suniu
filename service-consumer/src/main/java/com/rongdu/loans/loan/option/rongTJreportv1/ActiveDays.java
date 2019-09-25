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
public class ActiveDays implements Serializable{

	private static final long serialVersionUID = 6855297784606783680L;
	
	@JsonProperty("start_day")
    private String startDay;
    @JsonProperty("stop_3_days_detail")
    private List<String> stop3DaysDetail;
    @JsonProperty("stop_days_detail")
    private List<String> stopDaysDetail;
    @JsonProperty("total_days")
    private int totalDays;
    @JsonProperty("end_day")
    private String endDay;
    @JsonProperty("stop_days")
    private int stopDays;
    @JsonProperty("stop_3_days")
    private int stop3Days;

}
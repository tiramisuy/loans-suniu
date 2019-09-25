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
public class Json implements Serializable{

	private static final long serialVersionUID = -9074991885026273727L;
	
	@JsonProperty("trip_analysis")
    private List<TripAnalysis> tripAnalysis;
    @JsonProperty("user_portrait")
    private UserPortrait userPortrait;
    @JsonProperty("input_info")
    private InputInfo inputInfo;
    @JsonProperty("call_log")
    private List<CallLog> callLog;
    @JsonProperty("area_analysis")
    private List<AreaAnalysis> areaAnalysis;
    @JsonProperty("emergency_analysis")
    private List<EmergencyAnalysis> emergencyAnalysis;
    @JsonProperty("basic_info")
    private BasicInfo basicInfo;
    @JsonProperty("monthly_consumption")
    private List<MonthlyConsumption> monthlyConsumption;
    @JsonProperty("special_cate")
    private List<SpecialCate> specialCate;
    @JsonProperty("head_info")
    private HeadInfo headInfo;
    @JsonProperty("risk_analysis")
    private RiskAnalysis riskAnalysis;

}
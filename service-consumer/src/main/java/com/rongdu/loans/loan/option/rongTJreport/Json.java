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
public class Json implements Serializable{

	private static final long serialVersionUID = 2729641978330971329L;
	
	@JsonProperty("user_portrait")
    private UserPortrait userPortrait;
    @JsonProperty("input_info")
    private InputInfo inputInfo;
    @JsonProperty("call_log")
    private List<CallLog> callLog;
    @JsonProperty("contact_area_analysis")
    private List<ContactAreaAnalysis> contactAreaAnalysis;
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
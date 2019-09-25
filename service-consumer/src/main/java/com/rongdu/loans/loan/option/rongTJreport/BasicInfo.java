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
public class BasicInfo implements Serializable{

	private static final long serialVersionUID = 8287846991923529213L;
	
	@JsonProperty("phone_info")
    private String phoneInfo;
    @JsonProperty("reg_time")
    private String regTime;
    @JsonProperty("operator_id_card")
    private String operatorIdCard;
    @JsonProperty("operator_zh")
    private String operatorZh;
    @JsonProperty("id_card_check")
    private int idCardCheck;
    @JsonProperty("name_check")
    private int nameCheck;
    private String phone;
    private String operator;
    @JsonProperty("if_contact_emergency")
    private List<IfContactEmergency> ifContactEmergency;
    @JsonProperty("operator_name")
    private String operatorName;
    @JsonProperty("phone_location")
    private String phoneLocation;
    @JsonProperty("monthly_avg_consumption")
    private double monthlyAvgConsumption;
    @JsonProperty("cur_balance")
    private double curBalance;

}
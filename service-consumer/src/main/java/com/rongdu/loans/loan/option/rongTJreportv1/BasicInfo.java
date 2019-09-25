package com.rongdu.loans.loan.option.rongTJreportv1;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-19 11:4:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class BasicInfo implements Serializable{

	private static final long serialVersionUID = -8987051887874819311L;
	
	@JsonProperty("reg_time")
    private String regTime;
    @JsonProperty("operator_zh")
    private String operatorZh;
    private String phone;
    @JsonProperty("current_balance")
    private double currentBalance;
    @JsonProperty("id_card")
    private String idCard;
    @JsonProperty("name_check")
    private int nameCheck;
    @JsonProperty("real_name")
    private String realName;
    private String operator;
    @JsonProperty("if_call_emergency1")
    private int ifCallEmergency1;
    @JsonProperty("if_call_emergency2")
    private int ifCallEmergency2;
    @JsonProperty("phone_location")
    private String phoneLocation;
    @JsonProperty("ave_monthly_consumption")
    private double aveMonthlyConsumption;
    @JsonProperty("id_card_check")
    private int idCardCheck;

}
package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@lombok.Data
public class GjjBrief implements Serializable{

	private static final long serialVersionUID = 3812899093865984729L;
	
	@JsonProperty("ID")
    private String id;
    private String name;
    private String card;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("deposit_amount")
    private String depositAmount;
    @JsonProperty("fb_deposit_amount")
    private String fbDepositAmount;
    private String balance;
    @JsonProperty("fb_balance")
    private String fbBalance;
    @JsonProperty("once_balance")
    private String onceBalance;
    private String status;
    @JsonProperty("record_date")
    private String recordDate;
    private String company;
    @JsonProperty("deposit_base")
    private String depositBase;
    @JsonProperty("person_rate")
    private String personRate;
    @JsonProperty("company_rate")
    private String companyRate;
    @JsonProperty("init_date")
    private Date initDate;
    @JsonProperty("start_date")
    private String startDate;
    private String sex;
    private String email;
    private String phone;
    private String marriage;
    private String address;
    private Date birthday;
    @JsonProperty("company_id")
    private String companyId;
    @JsonProperty("person_deposit_amount")
    private String personDepositAmount;
    @JsonProperty("company_deposit_amount")
    private String companyDepositAmount;
    @JsonProperty("deposit_rate")
    private String depositRate;


}
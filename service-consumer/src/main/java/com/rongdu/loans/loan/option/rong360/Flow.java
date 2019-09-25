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
public class Flow implements Serializable{

	private static final long serialVersionUID = -5500145990455432434L;
	
	@JsonProperty("id_card")
    private String idCard;
    @JsonProperty("pay_date")
    private String payDate;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("base_rmb")
    private String baseRmb;
    @JsonProperty("com_rmb")
    private String comRmb;
    @JsonProperty("per_rmb")
    private String perRmb;
    @JsonProperty("balance_rmb")
    private String balanceRmb;
    @JsonProperty("month_rmb")
    private String monthRmb;
    @JsonProperty("com_name")
    private String comName;
    @JsonProperty("pay_type")
    private String payType;
    @JsonProperty("flow_type")
    private String flowType;


}
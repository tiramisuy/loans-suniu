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
public class GjjDetail implements Serializable{

	private static final long serialVersionUID = 8435476127251843638L;
	
	private String id;
    @JsonProperty("gjj_type")
    private String gjjType;
    private String company;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("record_date")
    private String recordDate;
    @JsonProperty("op_type")
    private String opType;
    @JsonProperty("record_month")
    private String recordMonth;
    private String amount;
    private String balance;
    private String remark;
    private String comments;
    @JsonProperty("deposit_type")
    private String depositType;
    @JsonProperty("cont_flag")
    private int contFlag;
    @JsonProperty("back_cont_flag")
    private int backContFlag;


}
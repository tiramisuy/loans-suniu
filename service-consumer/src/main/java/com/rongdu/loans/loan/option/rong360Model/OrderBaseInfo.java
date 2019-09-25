package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-03 10:38:10
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OrderBaseInfo implements Serializable{

	private static final long serialVersionUID = -5024180677059032200L;
	
	@JsonProperty("orderInfo")
    private Orderinfo orderinfo;
    @JsonProperty("applyDetail")
    private Applydetail applydetail;
    @JsonProperty("addInfo")
    private Addinfo addinfo;

}
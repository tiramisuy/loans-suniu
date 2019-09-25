package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class IfContactEmergency implements Serializable{

	private static final long serialVersionUID = 1494080266021371805L;
	
	@JsonProperty("if_tel")
    private int ifTel;
    @JsonProperty("if_msg")
    private int ifMsg;
    private int no;

}
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
public class CallLog implements Serializable{

	private static final long serialVersionUID = 2347481732814780403L;
	
	private String uid;
    private String type;
    private String date;
    private String duration;
    private String phone;
    @JsonProperty("phone_dirty")
    private String phoneDirty;
}
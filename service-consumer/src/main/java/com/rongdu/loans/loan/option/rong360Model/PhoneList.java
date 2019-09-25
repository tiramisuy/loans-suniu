package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-03 10:40:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class PhoneList implements Serializable{

	private static final long serialVersionUID = 3704582625836572212L;
	
	private String uid;
    @JsonProperty("phone_dirty")
    private String phoneDirty;
    private String phone;
    private String name;
    @JsonProperty("create_time")
    private String createTime;

}
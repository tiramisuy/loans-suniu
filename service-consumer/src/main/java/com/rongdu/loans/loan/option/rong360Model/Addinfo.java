package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-12 10:2:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Addinfo implements Serializable{

	private static final long serialVersionUID = -2206359267895295407L;
	
	private Mobile mobile;
    private Contacts contacts;
    @JsonProperty("asyncZhima")
    private int asynczhima;

}
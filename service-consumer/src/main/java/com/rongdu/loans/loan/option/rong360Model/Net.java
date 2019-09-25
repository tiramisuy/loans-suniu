package com.rongdu.loans.loan.option.rong360Model;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-12 10:2:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Net implements Serializable{

	private static final long serialVersionUID = -3874627186776205535L;
	
	@JsonProperty("netdata_key")
    private String netdataKey;
    private List<Netdata> netdata;

}
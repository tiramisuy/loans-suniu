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
public class Zhima implements Serializable{

	private static final long serialVersionUID = 5526298310795621514L;
	
	@JsonProperty("ali_trust_score")
    private AliTrustScore aliTrustScore;

}
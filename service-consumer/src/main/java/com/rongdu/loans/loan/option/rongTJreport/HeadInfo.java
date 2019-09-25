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
public class HeadInfo implements Serializable{

	private static final long serialVersionUID = -4172970493022095384L;
	
	@JsonProperty("report_time")
    private String reportTime;
    @JsonProperty("search_id")
    private String searchId;

}
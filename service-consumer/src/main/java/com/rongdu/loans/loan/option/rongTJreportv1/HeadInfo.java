package com.rongdu.loans.loan.option.rongTJreportv1;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-19 11:4:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class HeadInfo implements Serializable{

	private static final long serialVersionUID = -3475267255964778798L;
	
	@JsonProperty("report_time")
    private String reportTime;
    @JsonProperty("search_id")
    private String searchId;
    @JsonProperty("user_type")
    private int userType;

}
package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class InputInfo implements Serializable{

	private static final long serialVersionUID = -6993645926161062906L;
	
	private String phone;
    @JsonProperty("id_card")
    private String idCard;
    @JsonProperty("emergency_info")
    private List<EmergencyInfo> emergencyInfo;
    private String name;

}
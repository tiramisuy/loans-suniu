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
public class InputInfo implements Serializable{

	private static final long serialVersionUID = 6560341431925708801L;
	
	@JsonProperty("id_card")
    private String idCard;
    @JsonProperty("emergency_phone2")
    private String emergencyPhone2;
    @JsonProperty("emergency_phone1")
    private String emergencyPhone1;
    @JsonProperty("emergency_name2")
    private String emergencyName2;
    @JsonProperty("emergency_relation1")
    private String emergencyRelation1;
    @JsonProperty("emergency_name1")
    private String emergencyName1;
    private String phone;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("emergency_relation2")
    private String emergencyRelation2;

}
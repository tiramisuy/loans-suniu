package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@lombok.Data
public class AuthInfo implements Serializable{

	private static final long serialVersionUID = 2262647020522821520L;
	
	@JsonProperty("login_name")
    private String loginName;
    private String name;
    @JsonProperty("id_card")
    private String idCard;
    @JsonProperty("auth_time")
    private Date authTime;
    @JsonProperty("binding_phone")
    private String bindingPhone;
    @JsonProperty("auth_channel")
    private String authChannel;
    @JsonProperty("financial_service")
    private String financialService;

}
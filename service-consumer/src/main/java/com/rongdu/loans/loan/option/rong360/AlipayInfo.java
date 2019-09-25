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
public class AlipayInfo implements Serializable{

	private static final long serialVersionUID = -6186530552136433912L;
	
	@JsonProperty("login_name")
    private String loginName;
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("id_card")
    private String idCard;
    private String email;
    private String phone;
    @JsonProperty("taobao_name")
    private String taobaoName;
    @JsonProperty("identify_time")
    private String identifyTime;
    @JsonProperty("register_date")
    private String registerDate;
    @JsonProperty("is_real_name")
    private String isRealName;
    @JsonProperty("is_protected")
    private String isProtected;
    @JsonProperty("is_phone")
    private String isPhone;
    @JsonProperty("secret_level")
    private String secretLevel;
    private String balance;
    private String income;
    @JsonProperty("is_idcard")
    private String isIdcard;
    private String amount;
    @JsonProperty("total_amount")
    private String totalAmount;
    @JsonProperty("available_amount")
    private String availableAmount;
    @JsonProperty("need_to_pay_next_month")
    private String needToPayNextMonth;

}
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
public class User implements Serializable{

	private static final long serialVersionUID = -1563384941285381613L;
	
	@JsonProperty("user_source")
    private String userSource;
    @JsonProperty("id_card")
    private String idCard;
    private String addr;
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("phone_remain")
    private String phoneRemain;
    private String phone;
    @JsonProperty("reg_time")
    private String regTime;
    @JsonProperty("update_time")
    private String updateTime;
    private String score;
    @JsonProperty("contact_phone")
    private String contactPhone;
    @JsonProperty("star_level")
    private String starLevel;
    private String authentication;
    @JsonProperty("phone_status")
    private String phoneStatus;
    @JsonProperty("package_name")
    private String packageName;

}
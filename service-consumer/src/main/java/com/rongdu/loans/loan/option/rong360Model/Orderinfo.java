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
public class Orderinfo implements Serializable{

	private static final long serialVersionUID = 4667994588775896588L;
	
	@JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("user_id")
    private int userId;
    private int id;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_mobile")
    private String userMobile;
    @JsonProperty("register_time")
    private int registerTime;
    @JsonProperty("application_amount")
    private String applicationAmount;
    private int status;
    @JsonProperty("order_time")
    private int orderTime;
    private String city;
    private String bank;
    private String product;
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("user_group_id")
    private int userGroupId;
    private String platform;
    /* @JsonProperty("application_term")
    private int applicationTerm;*/
}
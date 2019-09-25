package com.rongdu.loans.loan.option.xjbk;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: AuthFeedBackOP.java  
* @author: yuanxianchu  
* @date 2018年8月20日  
* @version V1.0  
*/
@Data
public class AuthFeedBackOP implements Serializable {

	private static final long serialVersionUID = -128918510761589054L;

	/**
	 * 认证结果状态; 200 认证成功，401认证失败需要重新认证
	 */
	@JsonProperty("auth_status")
	private String authStatus;
	
	/**
	 * 认证类型 1 芝麻认证，2 运营商认证, 3 信用卡认证，4 公积金认证，
	 * 5 社保认证，6 人行征信认证 7 信用卡账单认证 8 用款确认
	 */
	@JsonProperty("auth_type")
	private String authType;
	
	/**
	 * 有效期时间戳(10位)
	 */
	@JsonProperty("success_time")
	private String successTime;
	
	/**
	 * 用户姓名
	 */
	@JsonProperty("user_name")
	private String userName;
	
	/**
	 * 用户手机号
	 */
	@JsonProperty("user_phone")
	private String userPhone;
	
	/**
	 * 用户身份证号码
	 */
	@JsonProperty("user_idcard")
	private String userIdcard;
}

package com.rongdu.loans.loan.option.xjbk;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: AuthStatus.java  
* @author: yuanxianchu  
* @date 2018年8月17日  
* @version V1.0  
*/
@Data
public class AuthStatus implements Serializable {

	private static final long serialVersionUID = -7589931649274152196L;
	
	/**
	 * 认证结果状态; 200 认证成功，401 未认证
	 */
	@JsonProperty("auth_result")
	private String authResult;
	
	/**
	 * 有效期时间戳
	 */
	@JsonProperty("success_time")
	private String successTime;

}

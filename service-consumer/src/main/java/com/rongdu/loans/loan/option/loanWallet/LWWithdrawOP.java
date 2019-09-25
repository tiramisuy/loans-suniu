package com.rongdu.loans.loan.option.loanWallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: CheckUserOP.java
* @author: fy
* @version V1.0  
*/
@Data
public class LWWithdrawOP implements Serializable{
	
	/**
	 * 	订单号
	 */
	@JsonProperty("orderId")
	private String orderId;

	/**
	 * 	用户唯一标识
	 */
	@JsonProperty("userId")
	private String userId;


	/**
	 * 	验证码
	 */
	@JsonProperty("verifyCode")
	private String verifyCode;

}

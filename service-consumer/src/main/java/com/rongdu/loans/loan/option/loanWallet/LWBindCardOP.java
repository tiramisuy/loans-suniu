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
public class LWBindCardOP implements Serializable{
	
	/**
	 * 	银行代码
	 */
	@JsonProperty("bankCode")
	private String bankCode;

	/**
	 * 	银行卡号
	 */
	@JsonProperty("cardNo")
	private String cardNo;

	/**
	 * 	用户唯一标识
	 */
	@JsonProperty("userId")
	private String userId;

	/**
	 * 	真实姓名
	 */
	@JsonProperty("realName")
	private String realName;

	/**
	 * 	身份证号
	 */
	@JsonProperty("idcard")
	private String idCard;

	/**
	 * 	手机号
	 */
	@JsonProperty("mobile")
	private String mobile;

	/**
	 * 	绑卡验证码
	 */
	@JsonProperty("verifyCode")
	private String verifyCode;

}

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
public class LWCheckUserOP implements Serializable{
	
	/**
	 * md5(手机号)
	 */
	@JsonProperty("md5")
	private String md5;
	
	/**
	 * 用户手机号,后三位掩码，如13419617***
	 */
	@JsonProperty("mobile")
	private String mobile;
	
	/**
	 * 用户姓名
	 */
	@JsonProperty("realName")
	private String realName;

}

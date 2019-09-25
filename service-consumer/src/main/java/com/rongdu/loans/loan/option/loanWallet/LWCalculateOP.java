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
public class LWCalculateOP implements Serializable{
	
	/**
	 * 	借款金额（单位：分）
	 */
	@JsonProperty("loanAmount")
	private Integer loanAmount;
	
	/**
	 * 借款期限（单位：天/月）
	 */
	@JsonProperty("loanTerm")
	private Integer loanTerm;
	
	/**
	 * 借款期数（分期业务参数）
	 */
	@JsonProperty("loanStage")
	private Integer loanStage;

}

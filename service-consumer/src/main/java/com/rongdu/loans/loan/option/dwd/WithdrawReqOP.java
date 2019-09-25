package com.rongdu.loans.loan.option.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: WithdrawReqOP.java  
* @Package com.rongdu.loans.loan.option.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class WithdrawReqOP implements Serializable{
	
	private static final long serialVersionUID = 4264888180996966383L;
	
	@JsonProperty("order_no")
	private String orderNo;
	
	@JsonProperty("loan_amount")
	private String loanAmount;
	
	@JsonProperty("loan_term")
	private int loanTerm;
	
	@JsonProperty("loan_use")
	private String loanUse;
	
	@JsonProperty("return_url")
	private String returnUrl;

}

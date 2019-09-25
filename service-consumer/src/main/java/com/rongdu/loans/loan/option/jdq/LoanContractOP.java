package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: LoanContractOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月16日  
* @version V1.0  
*/
@Data
public class LoanContractOP implements Serializable{

	private static final long serialVersionUID = 2072114867009925909L;
	
	/**
	 * 借点钱订单号
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;
	
	/**
	 * 借款期限
	 */
	@JsonProperty("loan_term")
	private String loanTerm;
	
	/**
	 * 借款金额
	 */
	@JsonProperty("loan_amount")
	private String loanAmount;
	
	/**
	 * 回调url (如果机构合同页面有特殊逻辑需要处理，且处理完需要回到借点钱原生页面，可回调该地址)
	 */
	@JsonProperty("success_return_url")
	private String successReturnUrl;

}

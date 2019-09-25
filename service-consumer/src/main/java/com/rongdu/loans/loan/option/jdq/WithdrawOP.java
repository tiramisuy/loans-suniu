package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: WithdrawOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月15日  
* @version V1.0  
*/
@Data
public class WithdrawOP implements Serializable{

	private static final long serialVersionUID = -2947002955524606689L;
	
	/**
	 * 借点钱订单号
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;
	
	/**
	 * 贷款金额，单位元 (如：5000.00)
	 */
	@JsonProperty("loan_amount")
	private String loanAmount;
	
	/**
	 * 贷款期数，单期产品默认值为1
	 */
	@JsonProperty("loan_periods")
	private String loanPeriods;

}

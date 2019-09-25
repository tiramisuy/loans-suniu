package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**  
* @Title: CalculateOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月14日  
* @version V1.0  
*/
@Data
public class CalculateOP implements Serializable{

	private static final long serialVersionUID = -4035820504043277105L;
	
	/**
	 * 借点钱订单号
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;
	
	/**
	 * 提现金额，单位元
	 */
	@JsonProperty("loan_amount")
	private BigDecimal loanAmount;

}

package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongContractStatusOP.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongContractStatusOP implements Serializable{

	private static final long serialVersionUID = -3375285536516437999L;
	
	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 * 合同签订状态	
	 */
	@JsonProperty("contract_status")
	private String contractStatus;
	
	/**
	 * 失败原因
	 */
	private String reason;

}

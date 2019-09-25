package com.rongdu.loans.loan.option.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: DealContractOP.java  
* @Package com.rongdu.loans.loan.option.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class DealContractOP implements Serializable{
	
	private static final long serialVersionUID = 6643967766568457862L;
	
	@JsonProperty("order_no")
	private String orderNo;
	
	@JsonProperty("contract_return_url")
	private String contractReturnUrl;

}

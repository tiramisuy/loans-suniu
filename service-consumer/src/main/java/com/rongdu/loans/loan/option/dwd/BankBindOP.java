package com.rongdu.loans.loan.option.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: BankBindOP.java  
* @Package com.rongdu.loans.loan.option.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class BankBindOP implements Serializable{
	
	private static final long serialVersionUID = -7754238660859049624L;

	@JsonProperty("need_confirm")
	private String needConfirm;
	
	@JsonProperty("deal_result")
	private String dealResult;
	
	private String reason;

}

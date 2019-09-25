package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: BankResultVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class BankResultVO implements Serializable{
	
	private static final long serialVersionUID = 6292979730778920295L;

	@JsonProperty("order_no")
	private String orderNo;
	
	@JsonProperty("bank_card")
	private String bankCard;
	
	@JsonProperty("bind_status")
	private Integer bindStatus;
	
	private String reason;

}

package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: CertAuthVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class CertAuthVO implements Serializable{

	private static final long serialVersionUID = -5057665893612911161L;
	
	@JsonProperty("is_reloan")
	private Integer isReloan;
	
	@JsonProperty("amount_type")
	private Integer amountType;
	
	@JsonProperty("approval_amount")
	private String approvalAmount;
	
	@JsonProperty("max_approval_amount")
	private String maxApprovalAmount;
	
	@JsonProperty("min_approval_amount")
	private String minApprovalAmount;
	
	@JsonProperty("range_amount")
	private String rangeAmount;
	
	@JsonProperty("approval_term")
	private String approvalTerm;
	
	@JsonProperty("term_unit")
	private String termUnit;
	
	@JsonProperty("credit_deadline")
	private String creditDeadline;
	
	@JsonProperty("card_list")
	private String cardList;
	
}

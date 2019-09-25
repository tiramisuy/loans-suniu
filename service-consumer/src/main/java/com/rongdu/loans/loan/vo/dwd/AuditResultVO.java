package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: AuditResultVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class AuditResultVO implements Serializable{
	
	private static final long serialVersionUID = 3275583954779087304L;

	@JsonProperty("order_no")
	private String orderNo;
	
	private Integer conclusion;
	
	@JsonProperty("approval_time")
	private String approvalTime;
	
	@JsonProperty("term_unit")
	private Integer termUnit;
	
	@JsonProperty("amount_type")
	private Integer amountType;
	
	@JsonProperty("term_type")
	private Integer termType;
	
	@JsonProperty("approval_amount")
	private Integer approvalAmount;
	
	@JsonProperty("approval_term")
	private Integer approvalTerm;
	
	@JsonProperty("credit_deadline")
	private String creditDeadline;
	
	private String reapply;
	
	private String reapplytime;
	
	@JsonProperty("refuse_time")
	private String refuseTime;

	@JsonProperty("pro_type")
	private Integer proType;
	
	private String remark;

	@JsonIgnore
	private String channelCode;

	public void setTermUnit(String termUnit) {
		switch (termUnit){
			case "D":
				this.termUnit = 1;
				break;
			case "M":
				this.termUnit = 2;
				break;
			case "W":
				this.termUnit = 3;
				break;
			case "Y":
				this.termUnit = 4;
				break;
			default:
				this.termUnit = 1;
				break;
		}
	}

}

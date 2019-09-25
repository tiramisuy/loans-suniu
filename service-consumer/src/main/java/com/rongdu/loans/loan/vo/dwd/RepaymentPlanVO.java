package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RepaymnetPlanVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class RepaymentPlanVO implements Serializable{
	
	private static final long serialVersionUID = 9052524113495158994L;
	
	@JsonProperty("order_no")
	private String orderNo;
	@JsonProperty("open_bank")
	private String openBank;
	@JsonProperty("bank_card")
	private String bankCard;
	@JsonProperty("repayment_plan")
	private List<RepaymentPlanDetail> repaymentPlan;

	@JsonIgnore
	private String channelCode;

}

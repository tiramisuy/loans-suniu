package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: WithdrawTrialVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class WithdrawTrialVO implements Serializable{

	private static final long serialVersionUID = 7142453125907773509L;
	
	@JsonProperty("daily_rate")
	private BigDecimal dailyRate;
	
	@JsonProperty("monthly_rate")
	private BigDecimal monthlyRate;
	
	@JsonProperty("receive_amount")
	private BigDecimal receiveAmount;
	
	@JsonProperty("service_fee")
	private BigDecimal serviceFee;
	
	@JsonProperty("pay_amount")
	private BigDecimal payAmount;
	
	@JsonProperty("trial_result_data")
	private List<RepaymentPlanDetail> trialResultData;

}
